package uk.axone.module1_project;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLOutput;
import java.util.*;


public class Inventory implements Comparable<Item> {

    //Declarations of Collections used
    private Map<Item, Integer> stock = new HashMap<>();

    private List<Item> listOfItems;
    private Map<Item, Integer> listOfItemsAndCodes;

    private Item item;
    private Item controlItem;


    //Constructor declaration
    public Inventory() throws InvalidDataException {
        try {
            this.load();
        } catch (InvalidDataException ide) {
            System.out.println("this one " + ide.getMessage());
        }
    }

    //PRIVATE METHOD load() to populate the data*********************************************************
    private void load() throws InvalidDataException {

        //initialisation of collections
        listOfItems = new ArrayList<>();
        listOfItemsAndCodes = new HashMap<>();

//Reading Excel Sheet for further attributes
        ExcelReader excelReader = new ExcelReader("InventoryData.xlsx", "Sheet1");
        //System.out.println(excelReader.getCellValue(0, 0));

        int rows = excelReader.getRowCount();
        int cols = excelReader.getColumnCount();


//simple printout of the contents of the Excel sheet
        System.out.println("Excel Sheet Print out");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                System.out.print(excelReader.getCellValue(i, j) + "     ");
            }
            System.out.println();
        }

        System.out.println("*****************************");


        // iter through the excel sheet and get an initial Map of Items and Item Codes

        for (int i = 1; i < rows; i++) {

            // 1)
            String excelItemCode = excelReader.getCellValue(i, 0);
            Integer intExcelItemCode = Integer.parseInt(excelItemCode);

            if (intExcelItemCode > 99 && intExcelItemCode < 1000) {


                // 2)
                String excelItemName = excelReader.getCellValue(i, 1);
                // 3)
                String excelItemDescription = excelReader.getCellValue(i, 2);
                // 4)
                String excelItemPrice = excelReader.getCellValue(i, 4);
                Double dblExcelItemPrice = Double.parseDouble(excelItemPrice);
                //double dblItemPrice = dblExcelItemPrice;

                item = new Item(excelItemName, intExcelItemCode, excelItemDescription, dblExcelItemPrice);
                System.out.println(item.getItemName() + item.getItemCode() + item.getItemDescription() + item.getItemPrice());

                // 5)
                String excelItemQuantity = excelReader.getCellValue(i, 3);
                Integer intExcelItemQuantity = Integer.parseInt(excelItemQuantity);

                //-----------Loading onto Collections------------------
                listOfItemsAndCodes.put(item, intExcelItemCode);
                stock.put(item, intExcelItemQuantity);

            } else {
                throw new InvalidDataException("Invalid Code");
            }
            //-------------end of data population----------------------


        } // end of for-loop populating data


        //****Separate for loop *************
        //Getting a list of Items objects alone

        listOfItems.addAll(listOfItemsAndCodes.keySet());


        ///******Separate for loop ******************
        //Print out of the list of Items alone
        System.out.println("listOfItems Contents: - Arrays list of items objects");

        for (Item x : listOfItems) {
            System.out.println(x.getItemName());
        }


        //Printing list of Items and their attributes after population from Excel file
        System.out.println("listOfItems and their attributes:");
        for (final Item y : listOfItems) {
            System.out.println(y.getItemName() + "  " + y.getItemCode() + "   " + y.getItemDescription() + "    " + y.getItemPrice());
        }

        //Printing Stock contents to be used by other classes
        System.out.println("Stock Contents:");
        for (final Map.Entry<Item, Integer> stockItem : stock.entrySet()) {
            System.out.println(stockItem.getKey().getItemName() + "   " + stockItem.getValue());
        }

        System.out.println("LIST OF ITEMS AND CODES");
        for (Map.Entry<Item, Integer> listCodesSet : listOfItemsAndCodes.entrySet()) {
            System.out.println(listCodesSet.getKey() + "  " + listCodesSet.getValue());

        }
    }   //END OF LOAD METHOD************************************************************************


    //PRIVATE METHOD to check for item******************************************************************
    public Item checkItem(Item it) throws ItemNotFoundException {


        boolean result = false;
        System.out.println(" it value recieved in checkItem method :  " + it.getItemCode() + it.getItemName());

        for (Map.Entry<Item, Integer> it1 : listOfItemsAndCodes.entrySet()) {

            if (it1.getValue().equals(it.getItemCode())) {

                //      System.out.println("ITem found");
                //      System.out.println(" it1 found in checkItem() is   " + it1 + "  " + it1.getValue() + it
                //      .getItemPrice());
                //      System.out.println(" it found in checkItem() is   " + it + it.getItemName() + it.getItemPrice
                //      ());

                it = it1.getKey();

                // controlItem = it1.getKey();

                //      System.out.println(" it value after it = it1" + it +  it.getItemName() + it.getItemCode() + it
                //      .getItemDescription());
                result = true;
                break;

            } else {

                System.out.println("Item not found yet");
                //throw new ItemNotFoundException("Item Not Found Exception");

            }

        } // - end of search for-loop
        if (result) {
            System.out.println("Item Found in checkItem() method");
            return it;

        } else {
            System.out.println("Item not found in checkItem() method. Throwing Exception");
            throw new ItemNotFoundException("Item not found Exception");
        }


    }  //END OF CHECK METHOD **********************************************************************************


    //  PUBLIC METHODS START HERE


//---------------------PUBLIC METHOD reduceStock()-----------------------------------------------------------
// boolean reduceStock(Item it, Integer quantity) throws ItemNotFoundException
//    o returns true if the operation is successful

    public Item reduceStock(Item itemToReduce, final Integer quantity) throws ItemNotFoundException,
            InvalidDataException {

        // check if item is found - exists
        try {
            controlItem = checkItem(itemToReduce);
        } catch (ItemNotFoundException inf) {
            System.out.println(inf.getMessage());
        }
        System.out.println("controlItem value at beginning of reduceStock method" + controlItem.getItemName() + controlItem.getItemCode() + controlItem.getItemPrice() + controlItem.getItemDescription() + stock.get(controlItem));

        final Integer oldQuantity = stock.get(controlItem);
        final Integer newQuantity;


        if (quantity < 0) {

            System.out.println("Invalid Quantity entered");

        } else if (quantity <= oldQuantity) {
            newQuantity = oldQuantity - quantity;
            if (newQuantity == 0) {
                System.out.println("Item" + "  " + controlItem.getItemName() + "  out of stock");
                stock.replace(controlItem, oldQuantity, newQuantity);
                System.out.println(controlItem.getItemName() + "  " + stock.get(controlItem));

            } else {
                stock.replace(controlItem, oldQuantity, newQuantity);
                System.out.println("replace statement  " + stock.get(controlItem));

            }

        } else if (quantity > oldQuantity) {

            System.out.println("Item quantity to be reduced is larger than in stock. Invalid.");

        }

        return controlItem;

    } //-----------------------------End of reduceStock()------------------------------------------------*/


    //------------------------------PUBLIC METHOD validateItem()----------------------------------
    //   - boolean validateItem(Item it) throws ItemNotFoundException
    //   o returns true if the itemCode of the Item passed matches a product in the list

    public boolean validateItem(Item newItem) throws ItemNotFoundException {

        if (listOfItemsAndCodes.containsValue(newItem.getItemCode())) {
            System.out.println("Item exists");
            return true;
        } else {

            throw new ItemNotFoundException("Item not FOUND Exception");

        }
    }
    // ------------------------end of validateItem()----------------------------------*/


//-----PUBLIC METHOD getItemStock()-------------------------------------------------------------------------
//- int getItemStock(Item it) throws ItemNotFoundException
//    o returns the number of items in stock for the Item passed.

    public int getItemStock(Item it) throws ItemNotFoundException {
        System.out.println("it received in getItemStock()  " + it.getItemCode() + it.getItemName());


        int itemStockQuantity;
        try {
            controlItem = checkItem(it);
            itemStockQuantity = stock.get(controlItem);
        } catch (ItemNotFoundException inf) {
            throw new ItemNotFoundException(inf.getMessage());
        }
        return itemStockQuantity;
    }
//-----------------------------End of getItemStock()--------------------------------------------------

    public Item getItem(Item it) throws ItemNotFoundException {
        System.out.println("it received in getItemStock()  " + it.getItemCode() + it.getItemName());

        for (Item it1 : listOfItems) {

            if (it1.getItemCode() == it.getItemCode()) {
                it = it1;
            }
        }
        return it;
    }


    /*-----------PUBLIC METHOD getItemCatalogue()-------------------------------------------------------
 - Map< Integer, String> getItemCatalouge()
     o returns itemCodes and ItemNames as a map
     o This can be used to identify what items are available to shop
     */
    public Map<Integer, String> getItemCatalogue() {
        Map<Integer, String> itemCatalogue = new HashMap<>();
        for (Item item : listOfItems) {

            itemCatalogue.put(item.getItemCode(), item.getItemName());

        }
        return itemCatalogue;
    }


    //Override method from Comparable Class

    @Override
    public int compareTo(Item o) {
        return 0;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}    // END OF CLASS

