(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .factory('IngredientAtHomeSearch', IngredientAtHomeSearch);

    IngredientAtHomeSearch.$inject = ['$resource'];

    function IngredientAtHomeSearch($resource) {
        var resourceUrl =  'api/_search/ingredient-at-homes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
