(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('QuantityMySuffixDetailController', QuantityMySuffixDetailController);

    QuantityMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Quantity'];

    function QuantityMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Quantity) {
        var vm = this;

        vm.quantity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coolcookApp:quantityUpdate', function(event, result) {
            vm.quantity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
