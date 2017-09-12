(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('CategoryMySuffixDetailController', CategoryMySuffixDetailController);

    CategoryMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Category'];

    function CategoryMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Category) {
        var vm = this;

        vm.category = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coolcookApp:categoryUpdate', function(event, result) {
            vm.category = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
