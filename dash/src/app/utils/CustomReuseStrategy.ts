import { RouteReuseStrategy, ActivatedRouteSnapshot, DetachedRouteHandle } from '@angular/router';
    
export class CustomReuseStrategy implements RouteReuseStrategy {

    routesToCache = ["", "tags/:tagname"]
    forTagsCashe : string[] = []

    // A map to cache components, using the route path as the key and the component instance as the value.
    cache: Map<string, DetachedRouteHandle | null> = new Map(); 

    // 1. Checks if the route configuration for the target route (future) matches the route configuration for the current route (curr).
    shouldReuseRoute(future: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot): boolean {
      
      console.log("future", future)
      console.log("curr", curr)
      if(future.routeConfig === curr.routeConfig){
        if((future.url[0]) && (curr.url[0]) && (future.url[0].path == "tags") && (curr.url[0].path == "tags")){
          if(future.url[1] != curr.url[1]){
            return false
          }
        }
        return true
      } 
      return false
    }

    // 2. Determines if a cached instance of the component for the target route exists.
    // If it returns true, Angular calls the `retrieve` method to restore the cached component.
    // If it returns false, Angular will create and initialize a new component instance.
    shouldAttach(route: ActivatedRouteSnapshot): boolean {
      const path = this.getPath(route);
      if(this.routesToCache.includes(path) && !!this.cache.get(path)){
        if(route.url[0] && route.url[0].path == "tags"){
          console.log("should attch?: ", route.url[0].path, route.url[1].path, this.forTagsCashe.includes(route.url[1].path))
          if(!this.forTagsCashe.includes(route.url[1].path)){
            return false
          }
        }
        return true
      }
      return false
    }

    // 3. Retrieves the cached component for the given route.
    retrieve(route: ActivatedRouteSnapshot): DetachedRouteHandle | null {
      let path : string = ""
      if(route.url[0] && route.url[0].path == "tags") {
        path = "tags/" + route.url[1].path 
      }else{
        path = this.getPath(route);
      }
      console.log("retriving: ", path, this.cache.get(path))
      return this.cache.get(path) || null;
    }

    // 4. Determines if the component we are navigating away from should be cached.
    // If it returns true, Angular calls the `store` method to save the component for future use.
    // If it returns false, Angular destroys the component.
    shouldDetach(route: ActivatedRouteSnapshot): boolean {
      const path = this.getPath(route);
      if(this.routesToCache.includes(path)){
        if(route.url[0] && route.url[0].path == "tags"){
          console.log("should dettch?: ", route.url[0].path, route.url[1].path, this.forTagsCashe.includes(route.url[1].path))
          if(!this.forTagsCashe.includes(route.url[1].path)){
            this.forTagsCashe.push(route.url[1].path)
          }
        }
        return true
      }
      return false
    }

    // Stores the component for the given route in the cache
    store(route: ActivatedRouteSnapshot, handle: DetachedRouteHandle | null): void {
      const protoPath = this.getPath(route);

      let path : string = ""
      if(route.url[0] && route.url[0].path == "tags") {
        path = "tags/" + route.url[1].path 
      }else{
        path = this.getPath(route);
      }
      console.log("storing: ", path, this.cache.get(path))
      if (this.routesToCache.includes(protoPath) && handle) {
        this.cache.set(path, handle);
      }
    }

    // Retrieves the path from the route's configuration.
    getPath(route: ActivatedRouteSnapshot): string {
      return route.routeConfig ? route.routeConfig.path || '' : '';
    }

}