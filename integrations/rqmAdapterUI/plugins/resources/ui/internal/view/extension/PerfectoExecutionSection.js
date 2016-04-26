dojo.provide ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.extension.PerfectoExecutionSection");

dojo.require ( "dijit._Widget");
dojo.require ( "dijit._Templated");

dojo.require ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.execution.PerfectoExecution");
dojo.require ( "com.ibm.rqm.execution.web.ui.internal.view.common.ExecutionViewer");

( function()
{ // module container

	var ExecutionViewer = com.ibm.rqm.execution.web.ui.internal.view.common.ExecutionViewer;
	var COMMAND = "command";
	var ARGUMENTS = "arguments"
	var PerfectoExecution = com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.execution.PerfectoExecution;
	dojo.declare ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.extension.PerfectoExecutionSection", [ dijit._Widget, dijit._Templated, ExecutionViewer ],
	{
		templateString : "<div dojoAttachPoint=\"_perfectoExecutionSection\" class=\"com-ibm-rqm-execution-web-ui-internal-view-extension-CommandLineExecutionSection\"></div>",

		constructor : function( args )
		{
		},

		postCreate : function()
		{
		},

		// @Overriden from ExecutionViewer
		initialize : function()
		{
			var script = this.script;
			var scriptInfo =
			{
				itemId : script.versionableItemId ? script.versionableItemId : script.itemId,
				type : script.scriptType.id,
				_resultId : this._resultId,
				parentWidget : this.parentEditor
			};
			if ( script != null )
			{
				if ( script.properties )
				{
					var scriptProperties = script.properties;
					var scriptPropertiesLength = scriptProperties.length;
					for ( var i = 0; i < scriptPropertiesLength; i++ )
					{
						if ( scriptProperties[ i ].propertyName === ARGUMENTS ) scriptInfo.arguments = scriptProperties[ i ].propertyValue;
					}
				}
			}
			this.scriptInfo = scriptInfo;
			dojox.xml.parser.removeChildren ( this._perfectoExecutionSection);
			this._cmdWidget = new PerfectoExecution (
			{
				parentController : this.parentController,
				ewiItemId : this.ewiItemId,
				scriptInfo : scriptInfo,
				parentSection : this
			});
			this._perfectoExecutionSection.appendChild ( this._cmdWidget.domNode);
			this.inherited ( "initialize", arguments);
		},

		// @Overriden from ExecutionViewer
		getHelpContextID : function()
		{
			return null;
		},

		// @Overriden from ExecutionViewer
		setCurrentRequest : function( request )
		{
			this._cmdWidget.showMessages ( request.response);
			this.inherited ( "setCurrentRequest", arguments);
		},

		// @Overriden from ExecutionViewer
		_finalizeForLog : function( resultId )
		{
			this._cmdWidget.finalize ();
			this.inherited ( "_finalizeForLog", arguments);
		}
	});

}) ();