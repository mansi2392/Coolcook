(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .factory('IngredientQtyMappingSearch', IngredientQtyMappingSearch);

    IngredientQtyMappingSearch.$inject = ['$resource'];

    function IngredientQtyMappingSearch($resource) {
        var resourceUrl =  'api/_search/ingredient-qty-mappings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
