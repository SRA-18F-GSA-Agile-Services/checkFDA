package com.sra.searchfda

class SearchController {

   def SearchService
	
   def search(String query) {
     render(SearchService.federatedSearch(query))
   }
}
