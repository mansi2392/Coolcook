(function() {
    'use strict';
    angular
        .module('coolcookApp')
        .factory('Quantity', Quantity);

    Quantity.$inject = ['$resource'];

    function Quantity ($resource) {
        var resourceUrl =  'api/quantities/:id';

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
