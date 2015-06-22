package com.sra.searchfda

class AnalysisController {

	def AnalysisService
	
    def index() {
		render(AnalysisService.test())
	}
}
