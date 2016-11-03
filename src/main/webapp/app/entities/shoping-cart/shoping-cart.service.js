(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('ShopingCart', ShopingCart);

    ShopingCart.$inject = ['$resource', 'DateUtils'];

    function ShopingCart ($resource, DateUtils) {
        var resourceUrl =  'api/shoping-carts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
