(function() {
    'use strict';
    angular
        .module('coolcookApp')
        .factory('IngredientQtyMapping', IngredientQtyMapping);

    IngredientQtyMapping.$inject = ['$resource'];

    function IngredientQtyMapping ($resource) {
        var resourceUrl =  'api/ingredient-qty-mappings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
