(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientMasterMySuffixDetailController', IngredientMasterMySuffixDetailController);

    IngredientMasterMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'IngredientMaster', 'Category'];

    function IngredientMasterMySuffixDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, IngredientMaster, Category) {
        var vm = this;

        vm.ingredientMaster = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('coolcookApp:ingredientMasterUpdate', function(event, result) {
            vm.ingredientMaster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
