package dao;

import db.DatabaseConnection;
import model.Account;
import model.Card;

import java.sql.*;
import java.time.LocalDate;

public class CardDAO {
    public static int saveCard(Card card, Connection connection) throws SQLException {
        String sql = "INSERT INTO card " +
                "(cardNumber, pin_code, expirationDate, CVV, account_ID) " +
                "VALUES (?,?,?,?,?)";

        try( PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, card.getCardNumber());
            stmt.setString(2, card.getPin_code());
            stmt.setString(3, String.valueOf(card.getExpirationDate()));
            stmt.setString(4, card.getCVV());
            stmt.setInt(5, card.getAccount_ID());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                card.setId(generatedId);   // actualizezi obiectul
                return generatedId;
            }
        }
        throw new SQLException("Failed to retrieve generated ID.");
    }


    public static Card getCardByAccountID(int accountID, Connection connection) throws SQLException {
        String sql = "SELECT id, cardNumber, pin_code, expirationDate, CVV, account_ID" +
                " FROM card WHERE account_ID = ?";

        try( PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, accountID);

            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                Card card = new Card();
                card.setId(resultSet.getInt("id"));
                card.setCardNumber(resultSet.getString("cardNumber"));
                card.setPin_code(resultSet.getString("pin_Code"));
                card.setExpirationDate(LocalDate.parse(resultSet.getString("expirationDate")));
                card.setCVV(resultSet.getString("CVV"));
                card.setAccount_ID(resultSet.getInt("account_ID"));

                return card;
            }
        }
        return null;
    }
}
