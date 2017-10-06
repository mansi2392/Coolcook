(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .factory('RecipeMasterSearch', RecipeMasterSearch);

    RecipeMasterSearch.$inject = ['$resource'];

    function RecipeMasterSearch($resource) {
        var resourceUrl =  'api/_search/recipe-masters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
