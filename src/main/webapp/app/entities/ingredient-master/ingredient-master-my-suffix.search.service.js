(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .factory('IngredientMasterSearch', IngredientMasterSearch);

    IngredientMasterSearch.$inject = ['$resource'];

    function IngredientMasterSearch($resource) {
        var resourceUrl =  'api/_search/ingredient-masters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
