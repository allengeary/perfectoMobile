/**
 * 
 */

dojo.provide ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.dialog.ScriptImport");

dojo.require ( "dojox.xml.parser");
dojo.require ( "dijit._Widget");
dojo.require ( "dijit._Templated");
dojo.require ( "com.ibm.asq.common.web.ui.internal.util.Utility");
dojo.require ( "com.ibm.asq.common.web.ui.internal.TransportServiceResponseWrapper");
dojo.require ( "com.ibm.asq.common.web.ui.internal.widgets.CommonDialog");
dojo.require ( "com.ibm.rqm.planning.web.client.internal.Constants");
dojo.require ( "com.ibm.rqm.execution.web.client.internal.execution.ToolAdapterRegistrationClient");
dojo.require ( "com.ibm.rqm.execution.web.client.internal.execution.ExecutionClient");
dojo.require ( "com.ibm.rqm.planning.web.ui.internal.view.table.TestScriptImportTableView");
dojo.require ( "com.ibm.rqm.execution.web.ui.internal.view.table.MachineAdapterTableView");
dojo.require ( "com.ibm.asq.common.web.client.internal.CommonPreferenceClient");

( function()
{
	var ASQCommonUtility = com.ibm.asq.common.web.ui.internal.util.Utility;
	var ServiceResponseHandler = com.ibm.asq.common.web.ui.internal.TransportServiceResponseWrapper;
	var Constants = com.ibm.rqm.planning.web.client.internal.Constants;
	var ExecutionClient = com.ibm.rqm.execution.web.client.internal.execution.ExecutionClient;
	var ToolAdapterRegistrationClient = com.ibm.rqm.execution.web.client.internal.execution.ToolAdapterRegistrationClient;
	var TestScriptImportTableView = com.ibm.rqm.planning.web.ui.internal.view.table.TestScriptImportTableView;
	var MachineAdapterTV = com.ibm.rqm.execution.web.ui.internal.view.table.MachineAdapterTableView;
	dojo.declare ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.dialog.ScriptImport", [ dijit._Widget, dijit._Templated ],
	{
		templateString : "<div class= \"com-ibm-rqm-execution-resourcebrowser-adapter-selector\" dojoAttachPoint=\"_adapterContainer\">" + "<table width=\"80%\">" + "<tbody><tr>"
				+ "<td style=\"display: none;\"><label style=\"font-family:Arial;font-size:9pt;\" dojoAttachPoint=\"_selectedAdapter\"></label></td>" + "<td style=\"display: none;\"><input dojoAttachPoint=\"_adapterName\"></input></td>"
				+ "<td><button style=\"font-family:Arial;font-size:9pt;width:100px;\" dojoAttachPoint=\"_onBrowse\" dojoAttachEvent=\"onclick: _onOpenAdapterDialog\"></button></td>" + "</tr><tr>"
				+ "<td width=\"10%\"><label style=\"font-family:Arial;font-size:9pt;\" dojoAttachPoint=\"_labelScriptPath\"></label></td>"
				+ "<td width=\"90%\" style=\"padding-right: 2px;\" colspan=\"2\"><input style=\"width:95%;\" dojoAttachPoint=\"_scriptPath\"></input></td>" + "</tr></tbody>" + "</table>" + "<div dojoAttachPoint=\"_adapterDialogNode\"></div>" + "</div>",

		dialogWidth : "80%",
		adapterId : -1,
		searchFor : "",
		prjPathLabel : Constants.getMessage ( "labelProjectPath"),
		pDlgTitle : Constants.getMessage ( "labelAdapterLocalResource"),
		stepTitle1 : Constants.getMessage ( "msgStep1AdapterSearch"),
		stepTitle2 : Constants.getMessage ( "msgStep2AdapterSearch"),
		qryResultLabel : Constants.getMessage ( "labelSelectTestScript"),

		constructor : function( args )
		{
		},

		postCreate : function()
		{

			this._labelScriptPath.innerHTML = Constants.getMessage ( "labelScriptPath");
			this._onBrowse.innerHTML = "<div style='white-space:nowrap'>" + Constants.getMessage ( "labelSelectAdapter") + "</div>";
			this._scriptPath.readOnly = true;
			this.browserMinimized = false;
			if ( this.insideScriptEditor == false )
			{
				dojo.style ( this._labelScriptPath, "display", "none");
				dojo.style ( this._scriptPath, "display", "none");
			}

			if ( this.script )
			{
				this.adapterId = this.script.adapterId ? this.script.adapterId : null;
				this._scriptPath.value = this.script.fullPath ? this.script.fullPath : "";
				this._scriptPath.title = this.script.fullPath ? this.script.fullPath : "";
				var responseHandler = new ServiceResponseHandler ( this, "_prefillEngines", "_error");

				ToolAdapterRegistrationClient.getAdapters ( responseHandler,
				{
					type : this.scriptType.id
				});
			}
		},

		_prefillEngines : function( engs )
		{
			var engines = engs && engs.length > 0 ? engs : [];
			var len = engines.length;
			for ( var i = 0; i < len; i++ )
			{
				if ( engines[ i ].id == this.adapterId )
				{
					this.selectedAdapter = engines[ i ];
					break;
				}
			}
		},

		initialize : function()
		{
		},

		setScriptType : function( scriptType )
		{
			this.scriptType = scriptType;
		},

		getProjectPath : function()
		{
			if ( this.dataStoreLocation ) return this.dataStoreLocation;
			else return this._scriptPath.value;
		},

		getSelectedAdapter : function()
		{
			return this.selectedAdapter;
		},

		_onOpenAdapterDialog : function( e )
		{
			if ( !this.scriptType.id )
			{
				console.error ( Constants.getMessage ( "errMissScriptTypeId"));
				return;
			}
			this._onBrowse.disabled = true;
			var adapterArgs =
			{
				scriptType : this.scriptType.id,
				loadingCompleteCallback : dojo.hitch ( this, this._createDialog),
				hideReservationNode : true
			};
			this.adapterTable = new MachineAdapterTV ( adapterArgs);
			if ( e )
			{
				dojo.stopEvent ( e);
			}

			if ( dojo.isFunction ( this.onOpen) ) this.onOpen ();
		},

		_createDialog : function( engineList )
		{
			var doc = document;
			dojox.xml.parser.removeChildren ( this._adapterDialogNode);

			this.topSection = doc.createElement ( 'div');
			this.middleSection = doc.createElement ( 'div');
			this.bottomSection = doc.createElement ( 'div');

			this.nextButton = doc.createElement ( 'button');
			this.nextButton.innerHTML = Constants.getMessage ( "labelNext");
			dojo.style ( this.nextButton, "fontSize", "1em");
			this.connect ( this.nextButton, "onclick", this._onNextClicked);

			this.backButton = doc.createElement ( 'button');
			this.backButton.innerHTML = Constants.getMessage ( "labelPrevious");
			dojo.style ( this.backButton, "fontSize", "1em");
			this.backButton.disabled = true;
			this.connect ( this.backButton, "onclick", this._onBackClicked);

			this.finishButton = doc.createElement ( 'button');
			this.finishButton.innerHTML = Constants.getMessage ( "labelFinish");
			dojo.style ( this.finishButton, "fontSize", "1em");
			this.connect ( this.finishButton, "onclick", this._onFinishClicked);
			this.finishButton.disabled = true;

			this.cancelButton = doc.createElement ( 'button');
			this.cancelButton.innerHTML = Constants.getMessage ( "labelCancel");
			dojo.style ( this.cancelButton, "fontSize", "1em");
			this.connect ( this.cancelButton, "onclick", this._onCancelClicked);

			var buttonDiv = doc.createElement ( 'span');
			buttonDiv.appendChild ( this.backButton);
			buttonDiv.appendChild ( this.nextButton);
			buttonDiv.appendChild ( this.finishButton);
			buttonDiv.appendChild ( this.cancelButton);

			var tableDiv = doc.createElement ( 'div');
			this.messageNode = doc.createElement ( 'span');
			tableDiv.appendChild ( this.messageNode);
			this.messageNode.innerHTML = this.stepTitle1;

			var table = doc.createElement ( 'table');
			table.width = "80%";
			var tbody = doc.createElement ( "tbody");
			table.appendChild ( tbody);

			tableDiv.appendChild ( table);
			this.projectPathNode = table;
			this.projectPathNode.style.display = "none";

			// Error Label & Error Value
			var tr2 = this._getEmptyRow ( doc);
			tbody.appendChild ( tr2);
			var tr3 = this._getEmptyRow ( doc);
			tbody.appendChild ( tr3);

			var tr4 = this._getEmptyRow ( doc);
			tbody.appendChild ( tr4);
			var td2_1 = this._getEmptyCell ( doc);
			td2_1.width = "10%";
			this.errorLabel = doc.createElement ( "label");
			this.errorLabel.innerHTML = Constants.getMessage ( "labelError");
			this.errorLabel.style.display = "none";
			td2_1.appendChild ( this.errorLabel);
			tr4.appendChild ( td2_1);

			var td2_2 = this._getEmptyCell ( doc);
			td2_2.width = "70%";
			this.errorValue = doc.createElement ( "label");
			this.errorValue.style.display = "none";
			td2_2.appendChild ( this.errorValue);
			tr4.appendChild ( td2_2);

			var td2_3 = this._getEmptyCell ( doc);
			td2_3.width = "20%";
			tr4.appendChild ( td2_3);

			this.topSection.appendChild ( tableDiv);
			this.middleSection.appendChild ( this.adapterTable.domNode);
			this.bottomSection.appendChild ( buttonDiv);

			var dialogArgs =
			{
				primaryTitle : this.pDlgTitle,
				showActions : false,
				isClosable : true,
				style : "z-index:9999;",
				onClose : dojo.hitch ( this, this._onCancelClicked)
			};
			this._adapterDialog = new com.ibm.asq.common.web.ui.internal.widgets.SelectionDialog ( dialogArgs);
			this._adapterDialog.setWidth ( this.dialogWidth);
			this._adapterDialog.contentContainer.appendChild ( this.topSection);
			this._adapterDialog.contentContainer.appendChild ( this.middleSection);
			this._adapterDialog.contentContainer.appendChild ( this.bottomSection);

			buttonDiv.style.cssFloat = "right";
			buttonDiv.style.Float = "right";
			this.selectedScript = null;
			this._adapterDialog.open ( false);

			// the table's content was created but it wasn't attached to any
			// parent dom node yet, until now. Need to refresh its contents to
			// get its table viewers to startup.
			this.adapterTable.refreshContents ();

			if ( this.adapterId > -1 )
			{
				var responseHandler = new ServiceResponseHandler ( this, "_presetAdapter", "_error");
				ToolAdapterRegistrationClient.getAdapters ( responseHandler,
				{

					type : this.scriptType.id
				});
			}
		},

		_presetAdapter : function( engs )
		{
			this._prefillEngines ( engs);
			this._onNextClicked ();
		},

		_onCancelClicked : function()
		{
			this._clearTimer ( true);
			this._adapterDialog.close ();
			this._onBrowse.disabled = false;
		},

		_onGoClicked : function()
		{
			// this.goButton.disabled = true;
			this.errorLabel.style.display = "none";
			this.errorValue.style.display = "none";
			this._clearTimer ();
			// if(this.pathValue.value == ""){
			// alert(Constants.getMessage("msgEnterProjectPath"));
			// this.goButton.disabled = false;
			// return;
			// }
			this._scriptTable.refresh ();
			this._scriptTable.showLoadingProgress ();
			var props = [];
			props.push (
			{
				propertyName : "scriptType",
				propertyValue : this.scriptType.id
			});
			props.push (
			{
				propertyName : "resourceType",
				propertyValue : this.searchFor
			});

			var requestObj =
			{
				selectedAdapterId : this.selectedAdapter.uuid,
				requestType : "IMPORT",
				properties : props,
				isNew : true
			};
			var responseHandler = new ServiceResponseHandler ( this, "_startChecking", "_error");
			ExecutionClient.postCreateRequest ( responseHandler, requestObj);
		},

		_onNextClicked : function()
		{
			this._clearTimer ();
			var adapter = this.adapterTable.getSelectedAdapter ();
			if ( !adapter )
			{
				alert ( Constants.getMessage ( "msgSelectAdapter"));
				return;
			}
			this.messageNode.innerHTML = this.stepTitle2;
			this.projectPathNode.style.display = "inline";

			this.selectedAdapter = adapter;
			this.nextButton.disabled = true;
			this.backButton.disabled = false;
			this.finishButton.disabled = true;
			dojo.style ( this.adapterTable.domNode, "display", "none");

			this._scriptTable = new TestScriptImportTableView (
			{
				singleSelect : false,
				scripts : [],
				queryName : this.qryResultLabel,
				rowCheckListener : dojo.hitch ( this, this._rowCheckListener)
			});

			var profiles = [];
			profiles.push (
			{
				'index' : 0,
				'name' : "Name",
				'width' : 70
			});
			profiles.push (
			{
				'index' : 1,
				'name' : "",
				'width' : 0
			});
			profiles.push (
			{
				'index' : 2,
				'name' : "Type",
				'width' : 30
			});
			this._scriptTable.tableViewer.columnsProfile = profiles;

			this._scriptTable.tableViewer.contentProvider.getColumnHeading = function( content, index )
			{
				var headings = [ Constants.getMessage ( "labelName"), "", Constants.getMessage ( "labelType") ];
				return headings[ index ];
			}

			this.middleSection.appendChild ( this._scriptTable.domNode);

			this._onGoClicked ();
		},

		_rowCheckListener : function( state )
		{
			this.finishButton.disabled = !state;
		},
		_startChecking : function( request )
		{
			this.requestId = request.id;
			this.parentEditor.setRequestId ( this.requestId);
			// Clear any timer that might be already existing
			this._clearTimer ();
			this._timer = setInterval ( dojo.hitch ( this, "fetchFiles"), 2000);
		},

		fetchFiles : function()
		{
			console.debug ( "fetchFiles");
			var importSelf = this;
			var callbackObj =
			{
				success : function( request )
				{
					if ( request && request.length > 0 && request[ 0 ].id == importSelf.requestId )
					{
						var requestObject = request[ 0 ];// Only one object should be returned
						// If the request if cancelled stop the polling
						if ( request.state == "com.ibm.rqm.executionframework.common.requeststate.cancelled" )
						{
							// Use constant
							importSelf._clearTimer ();
						}
						var percent = requestObject.progress;
						var responseObjects = requestObject.response;
						if ( percent == "100" )
						{
							importSelf._clearTimer ();
							var scriptNamesArray = [];
							var message = responseObjects ? responseObjects.data : "";
							// importSelf.goButton.disabled = false;
							if ( responseObjects )
							{
								var messagesArr = [];
								messagesArr = responseObjects.messages;
								if ( messagesArr )
								{
									var len = messagesArr.length;
									for ( var i = 0; i < len; i++ )
									{
										if ( messagesArr[ i ].statusCode == 404 )
										{
											var error = messagesArr[ i ].message;
											if ( error == "labelInvalidDir" )
											{
												importSelf.errorValue.innerHTML = Constants.getMessage ( "labelInvalidDir");
												importSelf.errorLabel.style.display = "inline";
												importSelf.errorValue.style.display = "inline";
											}
										}
									}
								}
							}
							var arr = [];
							var newMessage = message;
							try
							{
								newMessage = dojo.fromJson ( message);
							}
							catch ( e )
							{
								// For older adapters this message maynot be valid json string.
							}
							if ( newMessage.fileSeparator )
							{
								if ( newMessage.dataStoreLocation != null )
								{
									importSelf.dataStoreLocation = newMessage.dataStoreLocation;
								}
								arr.push ( newMessage.fileSeparator);
								var len = newMessage.resourceList.length;
								for ( var i = 0; i < len; i++ )
								{
									arr.push ( newMessage.resourceList[ i ]);
								}
							}
							else
							{
								// 1.0
								arr = message && message != "" ? message.split ( ":") : [];
							}
							if ( arr[ 0 ] != "-1" )
							{// File Seperater passed from the adapter
								if ( importSelf.parentEditor )
								{
									importSelf.parentEditor.setFileSeperater ( arr[ 0 ]);
								}
							}
							var len = arr.length;
							for ( var j = 1; j < len; j++ )
								// Skip the first in array
								scriptNamesArray.push ( arr[ j ]);
							importSelf._populateFileTable ( scriptNamesArray);
						}
					}
				},

				error : function( e )
				{
					importSelf._error ( e);
				}
			};

			var param =
			{
				requestId : importSelf.requestId
			};
			var responseHandler = new ServiceResponseHandler ( callbackObj, "success", "error");
			ExecutionClient.getRequestDTO ( responseHandler, param);
		},

		_populateFileTable : function( scripts )
		{
			this._scripts = scripts ? scripts : [];
			this._scriptTable.endLoadingProgress ();
			var tableObjects = [];
			var normalizedPath = "";
			if ( this.dataStoreLocation != null )
			{
				normalizedPath = this._normalizePath ( this.dataStoreLocation);
			}
			else
			{
				normalizedPath = this._normalizePath ( this.searchFor);
			}
			var _scripts = this._scripts;
			var len = _scripts.length;
			var id = this.scriptType.id;
			var name = this.scriptType.name;
			for ( var i = 0; i < len; i++ )
			{
				tableObjects.push (
				{
					type : id,
					scriptTypeName : name,
					id : i,
					name : _scripts[ i ],
					dataStoreLocation : ""
				});
			}
			this._scriptTable.refresh ( tableObjects);
			if ( this._adapterDialog ) this._adapterDialog.updatePosition ();
		},

		_onBackClicked : function()
		{
			this.errorLabel.style.display = "none";
			this.errorValue.style.display = "none";

			// In case going back from the page that's getting a list
			// Clear the timer
			this._clearTimer ( true);
			if ( this._scriptTable ) dojo.style ( this._scriptTable.domNode, "display", "none");
			dojo.style ( this.adapterTable.domNode, "display", "inline");
			this.messageNode.innerHTML = this.stepTitle1;
			this.projectPathNode.style.display = "none";
			this.nextButton.disabled = false;
			this.backButton.disabled = true;
			this.finishButton.disabled = true;
		},

		_onFinishClicked : function()
		{
			var scripts = this._scriptTable ? this._scriptTable.getSelectedRows () : [];
			
			var selectedPath = "";

			
			for ( var i=0; i<scripts.length; i++ )
			{
				var nextItem = "";
				
				//
				// Remove the device name on the device selection to conserve space
				//
				if ( this.pDlgTitle == "Select Devices" )
					nextItem = scripts[ i ].name.split( ":" )[ 1 ].trim();
				else
					nextItem = scripts[ i ].name;
				
				if ( (selectedPath.length + nextItem.length) > 247 )
				{
					alert( "Too many devices were selected - some of the devices have been truncated" );
					break;
				}
				
				selectedPath = selectedPath + nextItem;
				
				if ( i < scripts.length - 1 )
					selectedPath = selectedPath + ", ";
			}
			
			this.selectedScript = ( scripts && scripts.length > 0 ? scripts[ 0 ] : null);
			this.selectedScript.name = selectedPath;
			this._adapterDialog.close ();
			this._adapterDialog = null;
			this._clearTimer ();
			this._onBrowse.disabled = false;
			var scriptPath = this.searchFor;
			if ( this.dataStoreLocation )
			{
				scriptPath = this.dataStoreLocation + this.parentEditor.getFileSeperater () + this.selectedScript.name;
			}
			else if ( this.selectedScript && dojo.trim ( this.selectedScript.name).length > 0 )
			{
				scriptPath = scriptPath + this.parentEditor.getFileSeperater () + this.selectedScript.name;// Add appendFile check here
			}

			this._scriptPath.value = scriptPath;
			this._scriptPath.title = scriptPath;
			if ( dojo.isFunction ( this.onClose) ) this.onClose ();
		},

		_getEmptyRow : function( doc )
		{
			var row = doc.createElement ( "tr");
			return row;
		},

		_getEmptyCell : function( doc, span, align )
		{
			var cell = doc.createElement ( "td");
			if ( span != null )
			{
				cell.colSpan = span;
			}
			if ( align != null )
			{
				cell.align = align;
			}
			return cell;
		},

		getAttributes : function()
		{
			var normalizedPath = this._normalizePath ( this.searchFor);
			if ( this.dataStoreLocation )
			{
				// 1.0.1
				normalizedPath = this.dataStoreLocation;
			}
			if ( this.insideScriptEditor )
			{
				var param =
				{
					selectedAdapter : this.selectedAdapter,
					scriptPath : normalizedPath,
					selectedScript : this.selectedScript
				};
				return param;
			}
			else
			{
				var param =
				{
					selectedAdapter : this.selectedAdapter,
					scripts : this._scriptTable ? this._scriptTable.getSelectedRows () : []
				};
				return param;
			}
		},

		_normalizePath : function( path )
		{
			if ( path.lastIndexOf ( "/") == path.length - 1 || path.lastIndexOf ( "\\") == path.length - 1 )
			{
				return path.substring ( 0, path.length - 1);
			}
			else
			{
				return path;
			}
		},

		disconnectEvents : function()
		{
			this._onBrowse.disabled = true;
			this._scriptPath.readOnly = true;
		},

		connectEvents : function()
		{
			this._onBrowse.disabled = false;
			this._scriptPath.readOnly = false;
		},
		_clearTimer : function( cancelRequest )
		{
			console.debug ( "clearTimer");
			if ( this._timer )
			{
				clearInterval ( this._timer);
				delete this._timer;
			}
			if ( cancelRequest && this.selectedAdapter && this.requestId )
			{
				var params =
				{
					id : this.selectedAdapter.id,
					instructions : [
					{
						requestId : this.requestId,
						instructionType : "CANCEL"
					} ]
				};
				var responseHandler = new ServiceResponseHandler ( this, "_refreshCancelState", "_error");
				ToolAdapterRegistrationClient.postRegister ( responseHandler, params);
			}
		},
		_refreshCancelState : function()
		{
		},
		_error : function( errorObj )
		{
			if ( this._scriptTable )
			{
				this._scriptTable.endLoadingProgress ();
			}
			// if(this.goButton.disabled)
			// this.goButton.disabled = false;
			this._clearTimer ();
			console.error ( Constants.getMessage ( "errInAdapterSelector") + errorObj.message + " ");
		},
		uninitialize : function()
		{
			if ( this.adapterTable )
			{// Remove the table manually.
				ASQCommonUtility.destroyWidgetsFromNode ( this.middleSection);
			}
			console.debug ( "uninitialize");
			this._clearTimer ();
		}
	});
}) ();