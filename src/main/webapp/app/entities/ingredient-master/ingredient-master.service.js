(function() {
    'use strict';
    angular
        .module('coolcookApp')
        .factory('IngredientMaster', IngredientMaster);

    IngredientMaster.$inject = ['$resource'];

    function IngredientMaster ($resource) {
        var resourceUrl =  'api/ingredient-masters/:id';

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
