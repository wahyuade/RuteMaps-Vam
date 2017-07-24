package client_android.rutemaps.model;

import java.util.ArrayList;

/**
 * Created by wahyuade on 18/07/17.
 */

public class DoneResponseModel {
    private String total;
    private ArrayList<Detail> detail;

    public String getTotal() {
        return total;
    }

    public ArrayList<Detail> getDetail() {
        return detail;
    }

    public class Detail{
        private String nama_gudang;
        private String alamat_gudang;
        private String nama_taman;
        private String alamat_taman;
        private Integer stock_yang_dikirim;
        private Integer biaya;
        private Double lat_gudang;
        private Double lng_gudang;
        private Double lat_taman;
        private Double lng_taman;

        public String getNama_gudang() {
            return nama_gudang;
        }

        public String getAlamat_gudang() {
            return alamat_gudang;
        }

        public String getNama_taman() {
            return nama_taman;
        }

        public String getAlamat_taman() {
            return alamat_taman;
        }

        public Integer getStock_yang_dikirim() {
            return stock_yang_dikirim;
        }

        public Integer getBiaya() {
            return biaya;
        }

        public Double getLat_gudang() {
            return lat_gudang;
        }

        public Double getLng_gudang() {
            return lng_gudang;
        }

        public Double getLat_taman() {
            return lat_taman;
        }

        public Double getLng_taman() {
            return lng_taman;
        }
    }
}
