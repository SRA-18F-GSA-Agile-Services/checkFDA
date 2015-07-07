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
		"/styleguide"(view:'/styleguide')
		"500"(view:'/error')
		"404"(view:'/404')
	}
}
