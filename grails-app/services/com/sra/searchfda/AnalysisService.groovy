package com.sra.searchfda

import grails.transaction.Transactional

@Transactional
class AnalysisService {

	def bucket="fda-datasets"
	def localDatasets="c:/fda/datasets"
	
	//datasets: device-enforcement, device-event, drug-enforcement, drug-event, drug-label, food-enforcement
	
    def analyzeDataset(String dataset) {
    }
}
