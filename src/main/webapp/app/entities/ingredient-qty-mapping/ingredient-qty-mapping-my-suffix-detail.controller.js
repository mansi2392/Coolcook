(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientQtyMappingMySuffixDetailController', IngredientQtyMappingMySuffixDetailController);

    IngredientQtyMappingMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'IngredientQtyMapping', 'Quantity', 'IngredientMaster'];

    function IngredientQtyMappingMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, IngredientQtyMapping, Quantity, IngredientMaster) {
        var vm = this;

        vm.ingredientQtyMapping = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coolcookApp:ingredientQtyMappingUpdate', function(event, result) {
            vm.ingredientQtyMapping = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
