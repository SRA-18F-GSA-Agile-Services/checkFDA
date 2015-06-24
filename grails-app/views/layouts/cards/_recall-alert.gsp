<div class="card">
	<div class="content">
		<h2 class="header">${ recall.product_description }</h2>
		<div class="meta">
			<span>
				<i class="icon calendar outline"></i> ${ new Date().parse('yyyyMMdd', recall.recall_initiation_date).format('M/d/yyyy') }
			</span>
			<span>
				<i class="icon announcement"></i> ${ recall.voluntary_mandated }
			</span>
			<span>
				<i class="icon building outline"></i> ${ recall.recalling_firm }
			</span>
		</div>
		<div class="description">
			${ recall.reason_for_recall }
		</div>
	</div>
</div>