(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .factory('QuantitySearch', QuantitySearch);

    QuantitySearch.$inject = ['$resource'];

    function QuantitySearch($resource) {
        var resourceUrl =  'api/_search/quantities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
