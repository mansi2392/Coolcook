(function() {
    'use strict';
    angular
        .module('coolcookApp')
        .factory('IngredientAtHome', IngredientAtHome);

    IngredientAtHome.$inject = ['$resource', 'DateUtils'];

    function IngredientAtHome ($resource, DateUtils) {
        var resourceUrl =  'api/ingredient-at-homes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdAt = DateUtils.convertDateTimeFromServer(data.createdAt);
                        data.updatedAt = DateUtils.convertDateTimeFromServer(data.updatedAt);
                        data.expiryDate = DateUtils.convertDateTimeFromServer(data.expiryDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
