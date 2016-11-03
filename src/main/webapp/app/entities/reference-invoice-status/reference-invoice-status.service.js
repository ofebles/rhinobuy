(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('ReferenceInvoiceStatus', ReferenceInvoiceStatus);

    ReferenceInvoiceStatus.$inject = ['$resource', 'DateUtils'];

    function ReferenceInvoiceStatus ($resource, DateUtils) {
        var resourceUrl =  'api/reference-invoice-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.invoiceDate = DateUtils.convertDateTimeFromServer(data.invoiceDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
