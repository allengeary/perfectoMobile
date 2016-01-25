dojo.provide ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.execution.PerfectoExecution");

dojo.require ( "dijit._Widget");
dojo.require ( "dijit._Templated");
// Dojo widget

( function()
{ // module container

	dojo.declare ( "com.perfectomobile.integration.rqm.ui.plugin.ui.internal.view.execution.PerfectoExecution", [ dijit._Widget, dijit._Templated ],
	{
		templateString : "<div class=\"com-ibm-rqm-adapter-commandline-execution\">" + "<div dojoAttachPoint=\"_messages\"></div>" + "<br><div dojoAttachPoint=\"_dashboardLink\"></div>" + "</div>",

		dots : "",
		lastStatus : "",

		postCreate : function()
		{

		},

		showMessages : function( response )
		{
			if ( response )
			{
				dojox.xml.parser.removeChildren ( this._messages);
				var props = response.properties;
				if ( props )
				{
					var propsLength = props.length;
					for ( var i = 0; i < propsLength; i++ )
					{
						var propName = props[ i ].propertyName;
						var propValue = props[ i ].propertyValue;

						if ( propName == "Streaming Execution" )
						{
							var dashboardAnchor = document.createElement ( "a");
							dashboardAnchor.target = "_blank";
							dashboardAnchor.href = props[ i ].propertyValue;
							dashboardAnchor.appendChild ( document.createTextNode ( "Watch the execution LIVE!"));
							this._messages.appendChild ( dashboardAnchor );
							this._messages.appendChild ( document.createElement ( 'br'));
						}
						else
						{
							if ( propName == "Status" )
							{
								if ( propValue == this.lastStatus )
								{
									this.dots += "...";
								}
								else
								{
									this.dots = "";
								}
								this.lastStatus = propValue;
								propValue += this.dots;
							}
	
							var label = document.createElement ( 'label');
							label.id = propName;
							label.innerHTML = propName + ": " + propValue;
							this._messages.appendChild ( label);
							this._messages.appendChild ( document.createElement ( 'br'));
						}
					}
				}
			}
		},

		finalize : function()
		{
			dojox.xml.parser.removeChildren ( this._dashboardLink);
		}

	});
}) ();
