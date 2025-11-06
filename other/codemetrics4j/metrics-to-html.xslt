<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" indent="yes"/>

	<xsl:key name="pkgMetricNames" match="Package/Metrics/Metric" use="@name"/>
    <xsl:key name="clsMetricNames" match="Class/Metrics/Metric" use="@name"/>

	<xsl:template match="/">
		<html>
			<head>
				<title>Metrics Report</title>
				<link href="https://unpkg.com/tabulator-tables@6.3.1/dist/css/tabulator.min.css" rel="stylesheet"/>
				<script type="text/javascript" src="https://unpkg.com/tabulator-tables@6.3.1/dist/js/tabulator.min.js"/>
				<style>
					.download-button {
						margin-top: 10px;
						padding: 8px 16px;
						font-size: 14px;
						background-color: #4CAF50;
						color: white;
						border: none;
						border-radius: 4px;
						cursor: pointer;
					}
					.pulse-from-transparent {
						width: 100%;
						text-align: center;
						height: 50px;
						animation: transparentPulse 1s infinite alternate; /* Applies the animation */
					}

					@keyframes transparentPulse {
						0% {
							opacity: 0;
						}
						50% {
							opacity: 1;
						}
						100% {
							opacity: 0;
						}
					}
	
					:not(.hidden) {
						.loading {
					 		display: none;
						}
					}
					.hidden {
						max-height: 0px;
						button {
							display: none;
						}
						.loading {
							text-align: center;
							display: inline;
							min-height: 150px;
					}
					.metrics-section {
						margin-bottom: 40px;
						margin-left: 5px;
					}

				
				</style>

			</head>
			<body>
				<h2>Métricas de Projeto</h2>
				
				<div id="project-metrics" class="metrics-section hidden">
					<div class="loading">
						<p class="pulse-from-transparent">Loading ...</p>
					</div>
					<div id="project-metrics-data-table"></div>
					<div>
						<button class="download-button" id="project-metrics-download-csv">Download CSV</button>
					</div>
				</div>

				<h2>Métricas de Pacote</h2>
				<div id="package-metrics" class="metrics-section hidden">
					<div class="loading">
						<p class="pulse-from-transparent">Loading ...</p>
					</div>
					<div id="package-metrics-data-table"></div>
					<div>
						<button class="download-button" id="package-metrics-download-csv">Download CSV</button>
					</div>
				</div>

				<h2>Métricas de Classe</h2>
				<div id="class-metrics" class="metrics-section hidden">
					<div class="loading">
						<p class="pulse-from-transparent">Loading ...</p>
					</div>
					<div id="class-metrics-data-table"></div>
					<div>
						<button class="download-button" id="class-metrics-download-csv">Download CSV</button>
					</div>
				</div>
				
			</body>
				<script>
                    let uniqueIn = (arr, key) => {
                        const seen = new Set();
                        return arr.filter(item => {
                            const fieldValue = item[key];
                            if (seen.has(fieldValue)) {
                                return false; // Duplicate, filter it out
                            } else {
                                seen.add(fieldValue);
                                return true; // Unique, keep it
                            }
                        });
                    };

					let projectMetricsData = [
						<xsl:for-each select="/Project">
						{ 
							"id": "<xsl:value-of select="position()"/>", "name": "Application"<xsl:for-each select="./Metrics/Metric">, "<xsl:value-of select="@name"/>": "<xsl:value-of select="@value"/>"</xsl:for-each>
						},
						</xsl:for-each>
					]

					let packageMetricsData = [
						<xsl:for-each select="/Project/Packages/Package">
						{ 
							"id": "<xsl:value-of select="position()"/>", "name": "<xsl:value-of select="@name"/>"<xsl:for-each select="./Metrics/Metric">, "<xsl:value-of select="@name"/>": "<xsl:value-of select="@value"/>"</xsl:for-each>
						},
						</xsl:for-each>
					]

					let classMetricsData = [
						<xsl:for-each select="//Class">
						{ 
							"id": "<xsl:value-of select="position()"/>", "name": "<xsl:value-of select="../../@name"/>.<xsl:value-of select="@name"/>"<xsl:for-each select="./Metrics/Metric">, "<xsl:value-of select="@name"/>": "<xsl:value-of select="@value"/>"</xsl:for-each>
						},
						</xsl:for-each>
					]

                    let projectMetricsColumns = [
                        {"title": "name", "field": "name"}
                        <xsl:for-each select="/Project/Metrics/Metric">, {"title": "<xsl:value-of select="@description"/>", "field": "<xsl:value-of select="@name"/>", "headerTooltip": "<xsl:value-of select="@description"/>"}</xsl:for-each>
                    ]

                    let packageMetricsColumns = [
                        {"title": "name", "field": "name"}
                        <xsl:for-each select="//Project/Packages/Package[1]/Metrics/Metric">
                            , {"title": "<xsl:value-of select="@description"/>", "field": "<xsl:value-of select="@name"/>", "headerTooltip": "<xsl:value-of select="@description"/>"}
                        </xsl:for-each>
                    ]

                    let classMetricsColumns = [
                    {"title": "name", "field": "name"}
                    <xsl:for-each select="//Project/Packages/Package/Classes/Class[1]/Metrics/Metric">
                        , {"title": "<xsl:value-of select="@description"/>", "field": "<xsl:value-of select="@name"/>", "headerTooltip": "<xsl:value-of select="@description"/>"}
                    </xsl:for-each>
                    ]

                    let projectMetricsTable = new Tabulator("#project-metrics-data-table", {
						data:projectMetricsData,           //load row data from array
						layout:"fitColumns",      //fit columns to width of table
						responsiveLayout:"hide",  //hide columns that don't fit on the table
						addRowPos:"top",          //when adding a new row, add it to the top of the table
						history:true,             //allow undo and redo actions on the table
						pagination:"local",       //paginate the data
						paginationSize:7,         //allow 7 rows per page of data
						paginationCounter:"rows", //display count of paginated rows in footer
						movableColumns:true,      //allow column order to be changed
						initialSort:[             //set the initial sort order of the data
							{column:"name", dir:"asc"},
						],
						columnDefaults:{
							tooltip:true,         //show tool tips on cells
						},
    					columns: uniqueIn(projectMetricsColumns, "field")
					});


				let packageMetricsTable = new Tabulator("#package-metrics-data-table", {
						data:packageMetricsData,           //load row data from array
						layout:"fitDataTable",      //fit columns to width of table
						responsiveLayout:"hide",  //hide columns that don't fit on the table
						addRowPos:"top",          //when adding a new row, add it to the top of the table
						history:true,             //allow undo and redo actions on the table
						pagination:"local",       //paginate the data
						paginationSize:7,         //allow 7 rows per page of data
						paginationCounter:"rows", //display count of paginated rows in footer
						movableColumns:true,      //allow column order to be changed
						initialSort:[             //set the initial sort order of the data
							{column:"name", dir:"asc"},
						],
						columnDefaults:{
							tooltip:true,         //show tool tips on cells
						},
    					columns: uniqueIn(packageMetricsColumns, "field")
					});

					let classMetricsTable = new Tabulator("#class-metrics-data-table", {
						data:classMetricsData,           //load row data from array
						layout:"fitDataTable",      //fit columns to width of table
						responsiveLayout:"hide",  //hide columns that don't fit on the table
						addRowPos:"top",          //when adding a new row, add it to the top of the table
						history:true,             //allow undo and redo actions on the table
						pagination:"local",       //paginate the data
						paginationSize:25,         //allow 7 rows per page of data
						paginationCounter:"rows", //display count of paginated rows in footer
						movableColumns:true,      //allow column order to be changed
						initialSort:[             //set the initial sort order of the data
							{column:"name", dir:"asc"},
						],
						columnDefaults:{
							tooltip:true,         //show tool tips on cells
						},
                        columns: uniqueIn(classMetricsColumns, "field")
 					});

				
					document.getElementById("project-metrics-download-csv").addEventListener("click", function(){
   						projectMetricsTable.download("csv", "project-metrics-data.csv");
					});

					document.getElementById("package-metrics-download-csv").addEventListener("click", function(){
   						packageMetricsTable.download("csv", "package-metrics-data.csv");
					});

					document.getElementById("class-metrics-download-csv").addEventListener("click", function(){
   						classMetricsTable.download("csv", "class-metrics-data.csv");
					});

					classMetricsTable.on("tableBuilt", function(){
						document.getElementById("class-metrics").classList.remove("hidden");
						document.getElementById("package-metrics").classList.remove("hidden");
						document.getElementById("project-metrics").classList.remove("hidden");
					});

				</script>

		</html>
	</xsl:template>
</xsl:stylesheet>
