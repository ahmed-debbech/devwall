import { RouteReuseStrategy, ActivatedRouteSnapshot, DetachedRouteHandle } from '@angular/router';
    
export class CustomReuseStrategy implements RouteReuseStrategy {

    routesToCache = [""];

    // A map to cache components, using the route path as the key and the component instance as the value.
    cache: Map<string, DetachedRouteHandle | null> = new Map(); 

    // 1. Checks if the route configuration for the target route (future) matches the route configuration for the current route (curr).
    shouldReuseRoute(future: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot): boolean {
      return future.routeConfig === curr.routeConfig;
    }

    // 2. Determines if a cached instance of the component for the target route exists.
    // If it returns true, Angular calls the `retrieve` method to restore the cached component.
    // If it returns false, Angular will create and initialize a new component instance.
    shouldAttach(route: ActivatedRouteSnapshot): boolean {
      const path = this.getPath(route);
      return this.routesToCache.includes(path) && !!this.cache.get(path);
    }

    // 3. Retrieves the cached component for the given route.
    retrieve(route: ActivatedRouteSnapshot): DetachedRouteHandle | null {
      const path = this.getPath(route);
      return this.cache.get(path) || null;
    }

    // 4. Determines if the component we are navigating away from should be cached.
    // If it returns true, Angular calls the `store` method to save the component for future use.
    // If it returns false, Angular destroys the component.
    shouldDetach(route: ActivatedRouteSnapshot): boolean {
      const path = this.getPath(route);
      return this.routesToCache.includes(path);
    }

    // Stores the component for the given route in the cache
    store(route: ActivatedRouteSnapshot, handle: DetachedRouteHandle | null): void {
      const path = this.getPath(route);
      if (this.routesToCache.includes(path) && handle) {
        this.cache.set(path, handle);
      }
    }

    // Retrieves the path from the route's configuration.
    getPath(route: ActivatedRouteSnapshot): string {
      return route.routeConfig ? route.routeConfig.path || '' : '';
    }

}