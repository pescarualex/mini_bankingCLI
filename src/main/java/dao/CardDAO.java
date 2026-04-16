package dao;

import model.Card;

import java.sql.*;
import java.time.LocalDate;

public class CardDAO {
    public static int saveCard(Card card, Connection connection) throws SQLException {
        String sql = "INSERT INTO card " +
                "(cardNumber, pinCode, expirationDate, CVV, accountId) " +
                "VALUES (?,?,?,?,?)";

        try( PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, card.getCardNumber());
            stmt.setString(2, card.getPinCode());
            stmt.setString(3, String.valueOf(card.getExpirationDate()));
            stmt.setString(4, card.getCVV());
            stmt.setInt(5, card.getAccountId());

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
        String sql = "SELECT id, cardNumber, pinCode, expirationDate, CVV, accountId" +
                " FROM card WHERE account_ID = ?";

        try( PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, accountID);

            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                Card card = new Card();
                card.setId(resultSet.getInt("id"));
                card.setCardNumber(resultSet.getString("cardNumber"));
                card.setPinCode(resultSet.getString("pinCode"));
                card.setExpirationDate(LocalDate.parse(resultSet.getString("expirationDate")));
                card.setCVV(resultSet.getString("CVV"));
                card.setAccountId(resultSet.getInt("accountId"));

                return card;
            }
        }
        return null;
    }
}
