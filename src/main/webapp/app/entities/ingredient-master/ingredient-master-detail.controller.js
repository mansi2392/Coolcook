(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientMasterDetailController', IngredientMasterDetailController);

    IngredientMasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'IngredientMaster'];

    function IngredientMasterDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, IngredientMaster) {
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
