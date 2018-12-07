<#include "/macro/html-lichkin-simple.ftl"/>

<@html css=true i18nJs=true;section>
	<#if section="body-content">
		<div class="title">
			<div class="title-text"></div>
		</div>
		<div class="tabs tabs4">
			<div class="tab-button" id="tabButton_pending"></div>
			<div class="tab-button" id="tabButton_apply"></div>
			<div class="tab-button" id="tabButton_applied"></div>
			<div class="tab-button" id="tabButton_approved"></div>
			<div style="clear:both;"></div>
		</div>
		<div class="tab-content" id="tabContent_pending"></div>
		<div class="tab-content" id="tabContent_apply"></div>
		<div class="tab-content" id="tabContent_applied"></div>
		<div class="tab-content" id="tabContent_approved"></div>
	</#if>
</@html>
