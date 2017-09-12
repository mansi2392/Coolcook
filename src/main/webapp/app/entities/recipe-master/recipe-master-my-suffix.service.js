(function() {
    'use strict';
    angular
        .module('coolcookApp')
        .factory('RecipeMaster', RecipeMaster);

    RecipeMaster.$inject = ['$resource'];

    function RecipeMaster ($resource) {
        var resourceUrl =  'api/recipe-masters/:id';

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
