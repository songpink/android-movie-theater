package woowacourse.movie.ticket.contract

import woowacourse.movie.detail.model.Count
import woowacourse.movie.seats.model.Seat

interface MovieTicketContract {
    interface View {
        val presenter: Presenter

        fun showScreeningDate(info: String)

        fun showScreeningTime(info: String)

        fun showTicketView(
            movieTitle: String,
            moviePrice: Int,
            ticketCount: Int,
            seats: List<Seat>,
        )
    }

    interface Presenter {
        fun storeTicketCount(count: Count)

        fun setTicketInfo()

        fun storeScreeningDate(date: String)

        fun storeScreeningTime(time: String)

        fun setScreeningDateInfo()

        fun setScreeningTimeInfo()

        fun storeMovieId(id: Long)

        fun storePrice(price: Int)

        fun storeSeats(seats: List<Seat>)

//        fun setSeatsInfo()
    }
}
