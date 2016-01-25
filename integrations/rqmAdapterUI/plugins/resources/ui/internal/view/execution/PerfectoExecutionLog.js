dojo.provide ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.execution.PerfectoExecutionLog");

dojo.require ( "dijit._Widget");
dojo.require ( "dijit._Templated");
dojo.require ( "dojox.xml.parser");


dojo.require ( "com.ibm.rqm.execution.web.ui.internal.view.common.ImageCache");
dojo.require ( "com.ibm.rqm.execution.web.ui.internal.util.Utility");
// Clients
dojo.require ( "jazz.app.context");
( function()
{ // module container

	var DOWNLOAD_SERVICE_URI = "com.ibm.rqm.planning.service.internal.rest.IAttachmentRestService/"; // RQM

	var TeamServerClient = com.ibm.team.repository.web.transport.TeamServerClient;

	dojo.declare ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.execution.PerfectoExecutionLog", [ dijit._Widget, dijit._Templated ],
	{
		templatePath : dojo.moduleUrl ( "com.perfectomobile.integration.rqm.ui.plugin", "ui/internal/view/templates/execution/PerfectoExecutionLog.html"),

		postCreate : function()
		{
		},

		initialize : function()
		{
			var result = this.result;
			var resultProperties = result.properties;

			this.perfectoLogoImageNode.src = dojo.moduleUrl ( "com.perfectomobile.integration.rqm.ui.plugin", "ui/internal/view/images/perfecto.gif");
			dojo.style ( this.perfectoLogoImageNode, "display", "inline");

			if ( resultProperties )
			{

				var resultPropertiesLength = this.result.properties.length;
				for ( var i = 0; i < resultPropertiesLength; i++ )
				{
					if ( resultProperties[ i ].propertyName == "flowEndCode" )
					{
						this._printProperty ( "Status", resultProperties[ i ].propertyValue);
					}
					else if ( resultProperties[ i ].propertyName == "completionDescription" )
					{
						this._printProperty ( "Description", resultProperties[ i ].propertyValue);
					}
					else if ( resultProperties[ i ].propertyName == "reportLink" )
					{
						var reportLink = unescape ( resultProperties[ i ].propertyValue);
						var reportAnchor = document.createElement ( "a");
						reportAnchor.className = "actionLink";
						reportAnchor.target = "_blank";
						reportAnchor.href = reportLink;
						reportAnchor.appendChild ( document.createTextNode ( "Execution report - view on the Mobile Cloud"));

						this._resultLinkDetails.appendChild ( reportAnchor);
					}
				}
				this._detailsContainer.appendChild ( document.createElement ( "br"));
			}

			if ( result.attachments )
			{
				// Write CommandLine Test details here
				var resultAttachments = result.attachments;
				var len = resultAttachments.length;
				for ( var i = 0; i < len; i++ )
				{
					var text = "Execution report - view as pdf";
					this._resultFileDetails.appendChild ( this._getDownloadAnchor ( resultAttachments[ i ], text));
					this._resultFileDetails.appendChild ( document.createElement ( "br"));
				}
			}

		},

		_printProperty : function( name, value )
		{
			var label = document.createElement ( 'label');
			label.innerHTML = name + ": " + value;
			this._detailsContainer.appendChild ( label);

			this._detailsContainer.appendChild ( document.createElement ( "br"));
		},

		_getDownloadAnchor : function( attachment, text )
		{
			var saveAnchor = document.createElement ( "a");
			saveAnchor.className = "actionLink";
			saveAnchor.target = "_blank";
			saveAnchor.href = TeamServerClient._getServicePrefix () + DOWNLOAD_SERVICE_URI + attachment.itemId + "?attach=0";
			saveAnchor.appendChild ( document.createTextNode ( text));

			return saveAnchor;
		}
	});

}) ();