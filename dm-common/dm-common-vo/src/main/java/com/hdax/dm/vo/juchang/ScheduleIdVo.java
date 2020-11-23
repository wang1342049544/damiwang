package com.hdax.dm.vo.juchang;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScheduleIdVo implements Serializable {

        private Long scheduleId;
        private Long cinemaId;
        private List<SeatPriceListVo> seatPriceList = new ArrayList<>();
        private List<SeatInfoListVo> seatInfoList = new ArrayList<>();

        public Long getScheduleId() {
                return scheduleId;
        }

        public void setScheduleId(Long scheduleId) {
                this.scheduleId = scheduleId;
        }

        public Long getCinemaId() {
                return cinemaId;
        }

        public void setCinemaId(Long cinemaId) {
                this.cinemaId = cinemaId;
        }

        public List<SeatPriceListVo> getSeatPriceList() {
                return seatPriceList;
        }

        public void setSeatPriceList(List<SeatPriceListVo> seatPriceList) {
                this.seatPriceList = seatPriceList;
        }

        public List<SeatInfoListVo> getSeatInfoList() {
                return seatInfoList;
        }

        public void setSeatInfoList(List<SeatInfoListVo> seatInfoList) {
                this.seatInfoList = seatInfoList;
        }
}
