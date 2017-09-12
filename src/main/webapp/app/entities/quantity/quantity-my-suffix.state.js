(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('quantity-my-suffix', {
            parent: 'entity',
            url: '/quantity-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.quantity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/quantity/quantitiesmySuffix.html',
                    controller: 'QuantityMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('quantity');
                    $translatePartialLoader.addPart('unitOfQuantity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('quantity-my-suffix-detail', {
            parent: 'quantity-my-suffix',
            url: '/quantity-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.quantity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/quantity/quantity-my-suffix-detail.html',
                    controller: 'QuantityMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('quantity');
                    $translatePartialLoader.addPart('unitOfQuantity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Quantity', function($stateParams, Quantity) {
                    return Quantity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'quantity-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('quantity-my-suffix-detail.edit', {
            parent: 'quantity-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantity/quantity-my-suffix-dialog.html',
                    controller: 'QuantityMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Quantity', function(Quantity) {
                            return Quantity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('quantity-my-suffix.new', {
            parent: 'quantity-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantity/quantity-my-suffix-dialog.html',
                    controller: 'QuantityMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                qty: null,
                                unit: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('quantity-my-suffix', null, { reload: 'quantity-my-suffix' });
                }, function() {
                    $state.go('quantity-my-suffix');
                });
            }]
        })
        .state('quantity-my-suffix.edit', {
            parent: 'quantity-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantity/quantity-my-suffix-dialog.html',
                    controller: 'QuantityMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Quantity', function(Quantity) {
                            return Quantity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('quantity-my-suffix', null, { reload: 'quantity-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('quantity-my-suffix.delete', {
            parent: 'quantity-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantity/quantity-my-suffix-delete-dialog.html',
                    controller: 'QuantityMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Quantity', function(Quantity) {
                            return Quantity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('quantity-my-suffix', null, { reload: 'quantity-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
