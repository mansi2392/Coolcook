(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('RecipeMasterMySuffixDetailController', RecipeMasterMySuffixDetailController);

    RecipeMasterMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'RecipeMaster', 'Category', 'IngredientQtyMapping'];

    function RecipeMasterMySuffixDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, RecipeMaster, Category, IngredientQtyMapping) {
        var vm = this;

        vm.recipeMaster = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('coolcookApp:recipeMasterUpdate', function(event, result) {
            vm.recipeMaster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
