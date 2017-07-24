package client_android.rutemaps.model;

import java.util.ArrayList;

/**
 * Created by wahyuade on 18/07/17.
 */

public class DataTersimpanModel {
    private ArrayList<MarkerAttr> gudang;
    private ArrayList<MarkerAttr> taman;

    public ArrayList<MarkerAttr> getGudang() {
        return gudang;
    }

    public ArrayList<MarkerAttr> getTaman() {
        return taman;
    }

    public class MarkerAttr{
        private String _id;
        private String nama;
        private String stock;
        private String lat;
        private String lng;

        public String getStock() {
            return stock;
        }

        public String get_id() {
            return _id;
        }

        public String getNama() {
            return nama;
        }

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }
    }
}
