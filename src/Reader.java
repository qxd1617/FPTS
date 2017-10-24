/**The Reader Class reads through and parses the CSV File of
 * Stocks and then saves the Equities and Indexes to their
 * respective ArrayLists in the class.
 * Created by Owen on 3/2/2016.
 */
import java.io.*;
import java.util.ArrayList;

public class Reader implements java.io.Serializable{

    /**
     * ArrayList that contains all of the Stocks in the CSV.
     */
    public static ArrayList<Stock> stockMarket = new ArrayList<Stock>();

    /**
     * ArrayList that contains all of the Indexes in the CSV.
     */
    public static ArrayList<Index> indexMarket = new ArrayList<Index>();

    public static ArrayList<Equity> market = new ArrayList<Equity>();

    /**
     * Main function that loads and saves the contents of the stockMarket
     * ArrayList and the indexMarket ArrayList. Also calls the run function.
     */
    public static void main(){

        Reader obj = new Reader();
        obj.run("tmp/equities.txt");

        try {
            FileOutputStream out = new FileOutputStream("tmp/markets.ser");
            ObjectOutputStream output = new ObjectOutputStream(out);
            output.writeObject(obj);
            output.close();
        }
        catch(Exception e){

        }

        try {
            FileInputStream fileIn = new FileInputStream("tmp/markets.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Reader re = (Reader) in.readObject();
            in.close();
            fileIn.close();
        }
        catch(Exception e){
            System.out.println("Market class not found");
        }

        for(int x = 0; x < stockMarket.size(); x++){
            SearchScreen.newStockMarket.add(stockMarket.get(x));
        }
    }
    public static void updatePrice(Stock s){
        int index = stockMarket.indexOf(s);
            try {

                stockMarket.get(index).cost = Math.round(WebServices.Update(stockMarket.get(index).getSymbol()) * 100f) / 100f;

            } catch (Exception e) {
                stockMarket.get(index).cost = 0.00f;
            }

    }
    /**
     * Reads through and parses a CSV file, saving Stocks to the
     * stockMarket ArrayList and the Indexes, along with their
     * calculated costs, to the indexMarket ArrayList.
     * @param fileName - Name of file to be parsed.
     */
    public static void run(String fileName) {
        String csvFile = fileName;
        BufferedReader br = null;
        String line = "";
        String splitter = "\",\"";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitter);
                for (int i = 0; i < data.length; i += 1) {

                    String value = data[i].trim();
                    value = value.replace("\"", "");
                    data[i] = value;
                }
                Stock stock = new Stock(data[0], data[1], Float.parseFloat(data[2]));
                boolean indexFound = false;
                for (int i = 3; i < data.length ; i += 1){
                    for (Index obj: indexMarket){
                        if(data[i].equals(obj.name)){
                            indexFound = true;
                            stock.setIndex(obj);
                            obj.addComponent(stock);
                        }
                    }
                    if (indexFound == false){
                        Index newIndex = new Index(data[i]);

                        indexMarket.add(newIndex);
                        stock.setIndex(newIndex);
                        newIndex.addComponent(stock);
                    }
                }
                stockMarket.add(stock);

            }
        }

        catch(FileNotFoundException e) {
            System.out.println("File directory is: " + csvFile);
            }
        catch (IOException e) {
            e.printStackTrace();
            }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for (Index ind: indexMarket){
            ind.calculateCost();
        }
        market.addAll(indexMarket);
        market.addAll(stockMarket);
    }
}
