class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/"(controller:"search")
		"/controllers"(view:"/index")
		"/health"(controller:"home",action:"health")
		"/results"(controller:"search",action:"results")
		"500"(view:'/error')
		"404"(view:'/404')
	}
}
