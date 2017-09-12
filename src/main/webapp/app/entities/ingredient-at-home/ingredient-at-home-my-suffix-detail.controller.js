(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientAtHomeMySuffixDetailController', IngredientAtHomeMySuffixDetailController);

    IngredientAtHomeMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'IngredientAtHome', 'IngredientQtyMapping'];

    function IngredientAtHomeMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, IngredientAtHome, IngredientQtyMapping) {
        var vm = this;

        vm.ingredientAtHome = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coolcookApp:ingredientAtHomeUpdate', function(event, result) {
            vm.ingredientAtHome = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
