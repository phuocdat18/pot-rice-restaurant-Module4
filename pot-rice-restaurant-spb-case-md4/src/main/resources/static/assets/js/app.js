class App {
    static DOMAIN_SERVER = 'http://localhost:8088';

    // static DOMAIN_SERVER = 'http://192.168.1.103:8088';
    static API_SERVER = this.DOMAIN_SERVER + '/api';
    static  API_DASHBOARD = this.API_SERVER + '/dashboard';

    static showSuccessAlert(t) {
        Swal.fire({
            icon: 'success',
            title: t,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500,
        });
    }

    static showErrorAlert(t) {
        Swal.fire({
            icon: 'error',
            title: 'Warning',
            text: t,
        });
    }

}