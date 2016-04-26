dojo.provide ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.extension.PerfectoExecutionLogSection");

dojo.require ( "dijit._Widget");
dojo.require ( "dijit._Templated");

dojo.require ( "com.ibm.rqm.planning.web.ui.internal.view.extension.AbstractArtifactEditorSection");
dojo.require ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.execution.PerfectoExecutionLog");

( function()
{ // module container

	var AbstractArtifactEditorSection = com.ibm.rqm.planning.web.ui.internal.view.extension.AbstractArtifactEditorSection;
	var PerfectoExecutionLog = com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.execution.PerfectoExecutionLog;
	dojo.declare ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.extension.PerfectoExecutionLogSection", [ dijit._Widget, dijit._Templated, AbstractArtifactEditorSection ],
	{
		templateString : "<div dojoAttachPoint=\"_perfectoExecutionLogSection\" class=\"com-ibm-rqm-execution-web-ui-internal-view-extension-CommandLineExecutionLogSection\"></div>",

		constructor : function( args )
		{
		},

		postCreate : function()
		{
			this.inherited ( 'postCreate', arguments);
		},

		initialize : function()
		{
			dojox.xml.parser.removeChildren ( this._perfectoExecutionLogSection);
			var widgetArgs =
			{
				parentController : this.parentController,
				parentEditor : this,
				result : this.result
			};
			var _logViewer = new PerfectoExecutionLog ( widgetArgs);
			_logViewer.initialize ();
			this._perfectoExecutionLogSection.appendChild ( _logViewer.domNode);
			this._logViewer = _logViewer;

		},

		// @Overriden from AbstractArtifactEditorSection
		getHelpContextID : function()
		{
			return null;
		},

		// @Overriden from AbstractArtifactEditorSection
		getActions : function()
		{
			return [];
		},

		// @Overridden from AbstractArtifactEditorSection
		saveFinished : function()
		{
			// nothing right now
		},

		update : function()
		{
			this.initialize ();
		}
	});

}) ();