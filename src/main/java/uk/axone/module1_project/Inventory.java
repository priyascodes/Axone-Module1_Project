package uk.axone.module1_project;

import java.util.*;


public class Inventory implements Comparable<Item> {

    //Declarations of Collections used
    final private Map<Item, Integer> stock = new HashMap<>();

    private List<Item> listOfItems;
    private Map<Item, Integer> listOfItemsAndCodes;

    private Item item;
    private Item controlItem;


    //Constructor declaration
    public Inventory() throws InvalidDataException {
        this.load();

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
                System.out.println(item.getItemName() + "  " + item.getItemCode() + "  " + item.getItemDescription() + "  " + item.getItemPrice());

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
            System.out.println(listCodesSet.getKey().getItemName() + "  " + listCodesSet.getValue());

        }
    }   //END OF LOAD METHOD************************************************************************


    //PRIVATE METHOD to check for item******************************************************************
    private Item checkItem(Item it) throws ItemNotFoundException {


        boolean result = false;

        for (Map.Entry<Item, Integer> it1 : listOfItemsAndCodes.entrySet()) {

            if (it1.getValue().equals(it.getItemCode())) {

                controlItem = it1.getKey();

                result = true;
                break;

            }

        } // - end of search for-loop
        if (result) {
            return controlItem;

        } else {
            System.out.println("Item not found in checkItem() method. Throwing Exception");
            throw new ItemNotFoundException("Item not found Exception - checkItem()");
        }


    }  //END OF CHECK METHOD **********************************************************************************


    //  PUBLIC METHODS START HERE


//---------------------PUBLIC METHOD reduceStock()-----------------------------------------------------------
// boolean reduceStock(Item it, Integer quantity) throws ItemNotFoundException
//    o returns true if the operation is successful

    public boolean reduceStock(Item it, final Integer quantity) throws ItemNotFoundException {

        controlItem = checkItem(it);

        final Integer currentStockQuantity = stock.get(controlItem);
        final Integer newQuantity;


        if (quantity < 0) {

            System.out.println("Invalid Quantity entered");

        } else if (quantity <= currentStockQuantity) {
            newQuantity = currentStockQuantity - quantity;
            if (newQuantity == 0) {
                System.out.println("Item reduced in stock");
                System.out.println("Item" + "  " + controlItem.getItemName() + "now  out of stock");
                stock.replace(controlItem, currentStockQuantity, newQuantity);
                System.out.println(controlItem.getItemName() + "  " + stock.get(controlItem));
                return true;

            } else {
                stock.replace(controlItem, currentStockQuantity, newQuantity);
                System.out.println("Item Stock after reducing stock  " + stock.get(controlItem));
                return true;

            }

        } else {

            System.out.println("Item quantity to be reduced is larger than in stock. Invalid.");
            return false;

        }
        return false;

    } //-----------------------------End of reduceStock()------------------------------------------------*/


    //------------------------------PUBLIC METHOD validateItem()----------------------------------
    //   - boolean validateItem(Item it) throws ItemNotFoundException
    //   o returns true if the itemCode of the Item passed matches a product in the list

    public boolean validateItem(Item it) throws ItemNotFoundException {

        System.out.println("In validate() method");

        controlItem = checkItem(it);

        if (!controlItem.equals(it)) {
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

        int itemStockQuantity;
        controlItem = checkItem(it);
        itemStockQuantity = stock.get(controlItem);

        return itemStockQuantity;
    }
//-----------------------------End of getItemStock()--------------------------------------------------

    public Item getItem(Item it) {
        System.out.println("it received in getItemStock()  " + it.getItemCode() + "  " + it.getItemName());

        for (Item it1 : listOfItems) {

            if (it1.getItemCode() == it.getItemCode()) {
                controlItem = it1;
            }
        }
        return controlItem;
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
    public int compareTo(Item it) {

        if (item.getItemCode() > it.getItemCode()) {
            return -1;
        } else if (item.getItemCode() < it.getItemCode()) {
            return 1;
        } else {
            return 0;
        }
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

