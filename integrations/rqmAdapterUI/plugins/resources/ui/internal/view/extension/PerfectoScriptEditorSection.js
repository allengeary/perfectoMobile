dojo.provide ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.extension.PerfectoScriptEditorSection");

dojo.require ( "dijit._Widget");
dojo.require ( "dijit._Templated");
dojo.require ( "dojox.data.dom");

dojo.require ( "com.ibm.rqm.planning.web.ui.internal.view.extension.AbstractArtifactEditorSection");
dojo.require ( "com.ibm.rqm.planning.web.ui.internal.util.EditorUtil");
dojo.require ( "com.ibm.rqm.execution.web.client.internal.execution.RemoteExecutionScriptClient");
dojo.require ( "com.ibm.rqm.execution.web.ui.internal.view.common.RemoteExecutionScriptEditorSection");
dojo.require ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.dialog.ScriptImport");

( function()
{ // module container

	var AbstractArtifactEditorSection = com.ibm.rqm.planning.web.ui.internal.view.extension.AbstractArtifactEditorSection;
	var EditorUtil = com.ibm.rqm.planning.web.ui.internal.util.EditorUtil;
	var ScriptClient = com.ibm.rqm.execution.web.client.internal.execution.RemoteExecutionScriptClient;
	var SCRIPT_KEY = "scriptKey";
	var SCRIPT_NAME = "scriptName";
	var DUT = "DUT";
	var DEVICE = "Device";
	var REScriptEditorSection = com.ibm.rqm.execution.web.ui.internal.view.common.RemoteExecutionScriptEditorSection;
	var ScriptImport = com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.dialog.ScriptImport;

	dojo.declare ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.extension.PerfectoScriptEditorSection", [ dijit._Widget, dijit._Templated, AbstractArtifactEditorSection, REScriptEditorSection ],
	{
		templatePath : dojo.moduleUrl ( "com.perfectomobile.integration.rqm.ui.plugin", "ui/internal/view/templates/extension/PerfectoScriptEditorSection.html"),


		labelCommand : "Perfecto Mobile Script",
		labelArguments : "",
		sectionName : "Perfecto Mobile Section Editor",
		device : "",
		scriptKey : "",
		adapterId : -1,

		constructor : function( args )
		{
			this._script = args.script;
		},

		postCreate : function()
		{
		},

		initialize : function()
		{
			if ( this._script != null )
			{
				var _script = this._script;
				this._arguments.value = this.getScriptProperty ( _script.properties, DEVICE);
				this.device = this.getScriptProperty ( _script.properties, DUT);
				this.scriptKey = this.getScriptProperty ( _script.properties, SCRIPT_KEY);
				if ( this.getScriptProperty ( _script.properties, SCRIPT_NAME) == "" )
				{
					// make the path here
					if ( _script.managedByAdapter )
					{
						this._command.value = _script.fullPath;
						this._command.readOnly = false;
					}
					else
					{
						this._command.value = _script.sharePrefix + this.getFileSeperater () + _script.relativePath;
						this._command.readOnly = true;
					}
				}
				else this._command.value = this.getScriptProperty ( _script.properties, SCRIPT_NAME);
			}

			this._command.readOnly = true;
			this._arguments.readOnly = true;

			dojo.style ( this.perfectoLogo, "display", "inline");
			this.perfectoLogo.src = dojo.moduleUrl ( "com.perfectomobile.integration.rqm.ui.plugin", "ui/internal/view/images/perfecto.png");

			if ( !this.adapterSelector ) this.createAdapterScriptSelection ();
			if ( !this.adapterArgsSelector ) this.createAdapterArgumentsFileSelection ();

			if ( !this._argumentsHandle && !this.parentEditor.isReadOnly () ) this._argumentsHandle = this.connect ( this._arguments, "onkeypress", this.setDirty);
			if ( !this._commandHandle && this._script && this._script.managedByAdapter && !this.parentEditor.isReadOnly () ) this._commandHandle = this.connect ( this._command, "onkeypress", this.setDirty);
			if ( this._script && !this._script.managedByAdapter && this._commandHandle ) this.disconnect ( this._commandHandle);

		},

		setSelectedFiles : function( resource )
		{
			var fileList = resource.fileList;
			if ( fileList && fileList.length > 0 )
			{
				this._command.value = fileList[ 0 ].resourceURN;
			}
		},

		setAdapterScriptInfo : function( param )
		{
			if ( param.selectedScript )
			{
				this.scriptKey = param.selectedScript.name;
				this._command.value = this.scriptKey.substring ( this.scriptKey.indexOf ( ":") + 1);
			}
			else
			{
				this._command.value = "";
				this.scriptKey = "";
			}
		},

		getProperties : function()
		{
			var props = [];
			props.push (
			{
				propertyType : "string",
				propertyName : SCRIPT_KEY,
				propertyValue : this.scriptKey
			});
			props.push (
			{
				propertyType : "string",
				propertyName : SCRIPT_NAME,
				propertyValue : this._command.value
			});
			props.push (
			{
				propertyType : "string",
				propertyName : DUT,
				propertyValue : this.device
			});
			props.push (
			{
				propertyType : "string",
				propertyName : DEVICE,
				propertyValue : this._arguments.value
			});
			return props;
		},

		// @Overriden from RemoteExecutionScriptEditorSection
		getHelpContextID : function()
		{
			return null;
		},

		// @Overriden from RemoteExecutionScriptEditorSection
		getActions : function()
		{
			return [];
		},

		validate : function()
		{
			if ( dojo.trim ( this._command.value).length == 0 )
			{
				this.setValidationError ( "Required Field", this.labelCommand, this.sectionName);
				this._command.focus ();
				return false;
			}
			return true;
		},

		// @Overridden from RemoteExecutionScriptEditorSection
		saveFinished : function( testScript )
		{
			this._script = testScript;
			this.initialize ();
			this.setSectionDirty ( false);
		},

		updateAdapterRadioClicked : function()
		{
			if ( !this.parentEditor.isReadOnly () ) this._command.readOnly = false;
		},

		updateBrowseRadioListener : function()
		{
			this._command.readOnly = true;
		},

		createAdapterScriptSelection : function()
		{
			var args =
			{
				insideScriptEditor : this.insideScriptEditor,
				scriptType : this.scriptType,
				onClose : dojo.hitch ( this, "getScriptInfoFromAdapter"),
				onOpen : dojo.hitch ( this, "setScriptAdapter"),
				parentEditor : this
			};
			args.script = this._script;
			this.adapterSelector = new ScriptImport ( args);
			this._scriptSelectArea.appendChild ( this.adapterSelector.domNode);
			this.adapterUsage = true;

			dojo.style ( this.adapterSelector._labelScriptPath, "display", "none");
			dojo.style ( this.adapterSelector._scriptPath, "display", "none");

			this.adapterSelector._onBrowse.innerHTML = "<div style='white-space:nowrap'>" + "Select Script...     " + "</div>";
			this.adapterSelector.prjPathLabel = "";
			this.adapterSelector.pDlgTitle = "Select Script";
			this.adapterSelector.stepTitle2 = "";
			this.adapterSelector.searchFor = "scripts";

		},

		createAdapterArgumentsFileSelection : function()
		{
			var args =
			{
				insideScriptEditor : this.insideScriptEditor,
				scriptType : this.scriptType,
				onClose : dojo.hitch ( this, "getArgumentsInfoFromAdapter"),
				onOpen : dojo.hitch ( this, "setArgumentsAdapter"),
				parentEditor : this
			};
			args.script = this._script;
			this.adapterArgsSelector = new ScriptImport ( args);
			this._argsSelectArea.appendChild ( this.adapterArgsSelector.domNode);
			this.adapterUsage = true;

			dojo.style ( this.adapterArgsSelector._labelScriptPath, "display", "none");
			dojo.style ( this.adapterArgsSelector._scriptPath, "display", "none");

			this.adapterArgsSelector._onBrowse.innerHTML = "<div style='white-space:nowrap'>" + "Select Device..." + "</div>";
			this.adapterArgsSelector.prjPathLabel = "";
			this.adapterArgsSelector.pDlgTitle = "Select Devices";
			this.adapterArgsSelector.stepTitle2 = "";
			this.adapterArgsSelector.searchFor = "devices";

		},

		setScriptAdapter : function()
		{
			this.adapterSelector.adapterId = this.adapterId;
		},

		setArgumentsAdapter : function()
		{
			this.adapterArgsSelector.adapterId = this.adapterId;
		},

		getScriptInfoFromAdapter : function()
		{
			this.info = this.adapterSelector.getAttributes ();
			if ( this.insideScriptEditor ) this.parentEditor.setDirty ( true);
			this.setAdapterScriptInfo ( this.info);
			this.adapterId = this.adapterSelector.selectedAdapter.id;
		},

		getArgumentsInfoFromAdapter : function()
		{
			this.info = this.adapterArgsSelector.getAttributes ();
			if ( this.insideScriptEditor ) this.parentEditor.setDirty ( true);
			this.setAdapterDeviceInfo ( this.info);
			this.adapterId = this.adapterArgsSelector.selectedAdapter.id;
		},

		setAdapterDeviceInfo : function( param )
		{
			if ( param.selectedScript )
			{
				var deviceName = param.selectedScript.name;
				this._arguments.value = deviceName;
				this.device = deviceName.substr ( deviceName.lastIndexOf ( ": ") + 2);
			}
			else
			{
				this._arguments.value = "";
				this.device = "";
			}
		},

	});

}) ();