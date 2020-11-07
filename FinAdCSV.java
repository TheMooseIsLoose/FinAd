import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class FinAdCSV() {

    private static final String CSV_FILE_PATH = "E:\\Projects\\TKS-OP\\synt_transactions_10M.csv";

    private Map<String, Account> accounts;

    public enum Headers {
        category(0), timestamp(1), amount(6), balance(7), accountNumber(5), entryId(8);

        public final int index;

        private Headers(int index) {
            this.index = index;
        }
    }

    /*
        Uses Apache Commons csv library
     */
    public FinAdCSV(String filePath) {
        accounts = new HashMap<String, Account>();

        Reader csvReader = new FileReader(filePath);

        // Iterate csv file, create and add transactions to accounts
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            String category = record.get(Headers.category);
            String timestamp = record.get(Headers.timestamp.index);
            String amount = record.get(Headers.amount.index);
            String balance = record.get(Headers.balance.index);
            String accountNumber = record.get(Headers.accountNumber.index);
            int entryId = Integer.parseInt(record.get(Headers.entryId));

            Transaction transaction = new Transaction(parseTransactionDate(timestamp), new BigDecimal(amount), new BigDecimal(balance), category, entryId);
            addTransaction(accountNumber, transaction);
        }
    }

    private Calendar parseTransactionDate(String timestamp) {
        // 2019-01-04 11:26:21.308411214
        SimpleDateFormat transactionDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

        Calendar transactionDate = Calendar.getInstance();
        try {
            transactionDate.setTime(transactionDateFormat.parse(timestamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return transactionDate;
    }

    private void addTransaction(String accountNumber, Transaction transaction) {
        Account account;
        if (accounts.get(accountNumber) != null) {
            account = (Account) accounts.get(accountNumber);
            account.addTransaction(transaction);
        } else {
            account = new Account(Integer.parseInt(accountNumber));
            account.addTransaction(transaction);
            accounts.put(accountNumber, account);
        }
    }

}