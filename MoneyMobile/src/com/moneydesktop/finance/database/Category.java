
package com.moneydesktop.finance.database;

import com.moneydesktop.finance.data.Constant;
import com.moneydesktop.finance.model.User;
import com.moneydesktop.finance.util.Enums.DataState;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS
// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table CATEGORY.
 */
public class Category extends BusinessObject {

    private Long id;
    private String categoryId;
    private String categoryName;
    private String categoryNumber;
    private String imageName;
    private Boolean isSystem;
    private Boolean isTaxRelated;
    private String notes;
    private Integer sortOrder;
    private String taxReference;
    private Long categoryTypeId;
    private Long parentCategoryId;
    private long businessObjectId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient CategoryDao myDao;

    private CategoryType categoryType;
    private Long categoryType__resolvedKey;

    private Category parent;
    private Long parent__resolvedKey;

    private BusinessObjectBase businessObjectBase;
    private Long businessObjectBase__resolvedKey;

    private List<Category> children;
    private List<Transactions> transactions;
    private List<BudgetItem> budgetItems;

    // KEEP FIELDS - put your custom fields here

    public static final String TAG = "Category";

    // KEEP FIELDS END

    public Category() {
    }

    public Category(Long id) {
        this.id = id;
    }

    public Category(Long id, String categoryId, String categoryName, String categoryNumber,
            String imageName, Boolean isSystem, Boolean isTaxRelated, String notes,
            Integer sortOrder, String taxReference, Long categoryTypeId, Long parentCategoryId,
            long businessObjectId) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryNumber = categoryNumber;
        this.imageName = imageName;
        this.isSystem = isSystem;
        this.isTaxRelated = isTaxRelated;
        this.notes = notes;
        this.sortOrder = sortOrder;
        this.taxReference = taxReference;
        this.categoryTypeId = categoryTypeId;
        this.parentCategoryId = parentCategoryId;
        this.businessObjectId = businessObjectId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCategoryDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(String categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public Boolean getIsTaxRelated() {
        return isTaxRelated;
    }

    public void setIsTaxRelated(Boolean isTaxRelated) {
        this.isTaxRelated = isTaxRelated;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getTaxReference() {
        return taxReference;
    }

    public void setTaxReference(String taxReference) {
        this.taxReference = taxReference;
    }

    public Long getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(Long categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    @Override
    public long getBusinessObjectId() {
        return businessObjectId;
    }

    @Override
    public void setBusinessObjectId(long businessObjectId) {
        this.businessObjectId = businessObjectId;
    }

    /** To-one relationship, resolved on first access. */
    public CategoryType getCategoryType() {
        if (categoryType__resolvedKey == null || !categoryType__resolvedKey.equals(categoryTypeId)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoryTypeDao targetDao = daoSession.getCategoryTypeDao();
            categoryType = targetDao.load(categoryTypeId);
            categoryType__resolvedKey = categoryTypeId;
        }
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
        categoryTypeId = categoryType == null ? null : categoryType.getId();
        categoryType__resolvedKey = categoryTypeId;
    }

    /** To-one relationship, resolved on first access. */
    public Category getParent() {
        if (parent__resolvedKey == null || !parent__resolvedKey.equals(parentCategoryId)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoryDao targetDao = daoSession.getCategoryDao();
            parent = targetDao.load(parentCategoryId);
            parent__resolvedKey = parentCategoryId;
        }
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
        parentCategoryId = parent == null ? null : parent.getId();
        parent__resolvedKey = parentCategoryId;
    }

    /** To-one relationship, resolved on first access. */
    @Override
    public BusinessObjectBase getBusinessObjectBase() {
        if (businessObjectBase__resolvedKey == null
                || !businessObjectBase__resolvedKey.equals(businessObjectId)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BusinessObjectBaseDao targetDao = daoSession.getBusinessObjectBaseDao();
            businessObjectBase = targetDao.load(businessObjectId);
            businessObjectBase__resolvedKey = businessObjectId;
        }
        return businessObjectBase;
    }

    @Override
    public void setBusinessObjectBase(BusinessObjectBase businessObjectBase) {
        if (businessObjectBase == null) {
            throw new DaoException(
                    "To-one property 'businessObjectId' has not-null constraint; cannot set to-one to null");
        }
        this.businessObjectBase = businessObjectBase;
        businessObjectId = businessObjectBase.getId();
        businessObjectBase__resolvedKey = businessObjectId;
    }

    /**
     * To-many relationship, resolved on first access (and after reset). Changes
     * to to-many relations are not persisted, make changes to the target
     * entity.
     */
    public synchronized List<Category> getChildren() {
        if (children == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoryDao targetDao = daoSession.getCategoryDao();
            children = targetDao._queryCategory_Children(id);
        }
        return children;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a
     * fresh result.
     */
    public synchronized void resetChildren() {
        children = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset). Changes
     * to to-many relations are not persisted, make changes to the target
     * entity.
     */
    public synchronized List<Transactions> getTransactions() {
        if (transactions == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TransactionsDao targetDao = daoSession.getTransactionsDao();
            transactions = targetDao._queryCategory_Transactions(id);
        }
        return transactions;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a
     * fresh result.
     */
    public synchronized void resetTransactions() {
        transactions = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset). Changes
     * to to-many relations are not persisted, make changes to the target
     * entity.
     */
    public synchronized List<BudgetItem> getBudgetItems() {
        if (budgetItems == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BudgetItemDao targetDao = daoSession.getBudgetItemDao();
            budgetItems = targetDao._queryCategory_BudgetItems(id);
        }
        return budgetItems;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a
     * fresh result.
     */
    public synchronized void resetBudgetItems() {
        budgetItems = null;
    }

    /**
     * Convenient call for {@link AbstractDao#delete(Object)}. Entity must
     * attached to an entity context.
     */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link AbstractDao#update(Object)}. Entity must
     * attached to an entity context.
     */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * Convenient call for {@link AbstractDao#refresh(Object)}. Entity must
     * attached to an entity context.
     */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here

    @Override
    public void setExternalId(String id) {
        setCategoryId(id);
        getBusinessObjectBase().setExternalId(id);
    }

    @Override
    public String getExternalId() {
        return getCategoryId();
    }

    public static Category saveCategory(JSONObject json, boolean delete) {

        Category category = (Category) saveObject(json, Category.class, delete);

        // Object was deleted no need to continue
        if (category == null)
            return null;

        category.setCategoryName(json.optString(Constant.KEY_NAME));
        category.setIsSystem(User.getCurrentUser().getUserId()
                .equals(json.optString(Constant.KEY_USER_GUID)));

        String parentGuid = json.optString(Constant.KEY_PARENT_GUID);

        if (!parentGuid.equals(Constant.VALUE_NULL)) {

            Category parent = (Category) getObject(Category.class, parentGuid);

            if (parent != null) {

                category.setParent(parent);
                parent.acceptChanges();
                parent.updateBatch();
            }
        }

        String imageName = getIconForId(category);
        category.setImageName(imageName);

        boolean isIncome = json.optBoolean(Constant.KEY_INCOME);

        CategoryType categoryType = (CategoryType) getObject(CategoryType.class, isIncome ? "1"
                : "2");

        if (categoryType != null) {

            category.setCategoryType(categoryType);
            categoryType.acceptChanges();
            categoryType.updateBatch();
        }

        category.acceptChanges();

        return category;
    }

    @Override
    public JSONObject getJson() throws JSONException {

        JSONObject json = new JSONObject();

        if (getBusinessObjectBase().getDataStateEnum() == DataState.DATA_STATE_DELETED) {

            if (getCategoryId() != null)
                json.put(Constant.KEY_GUID, getCategoryId());

            return json;
        }

        if (getBusinessObjectBase().getDataStateEnum() != DataState.DATA_STATE_NEW
                && getCategoryId() != null)
            json.put(Constant.KEY_GUID, getCategoryId());

        if (getCategoryName() != null)
            json.put(Constant.KEY_NAME, getCategoryName());

        if (getCategoryType() != null && getCategoryType().getCategoryTypeId() != null)
            json.put(Constant.KEY_INCOME,
                    (Integer.parseInt(getCategoryType().getCategoryTypeId()) == 1) ? 1 : 0);

        if (getParent() != null)
            json.put(Constant.KEY_PARENT_GUID, getParent().getCategoryId());

        if (getExternalId() != null)
            json.put(Constant.KEY_EXTERNAL_ID, getExternalId());

        json.put(Constant.KEY_IS_DELETED, isDeleted() ? 1 : 0);
        json.put(Constant.KEY_REVISION, getBusinessObjectBase().getVersion());
        json.put(Constant.KEY_USER_GUID, User.getCurrentUser().getUserId());

        return json;
    }

    private static String getIconForId(Category category) {
        try {
            while (category.getParent() != null) {
                category = category.getParent();
            }
        } catch (Exception e) {

        }
        String categoryId = category.getCategoryId();

        String ret = "S"; // empty circle?

        if (categoryId.equals("CAT-0cb1d99d-f558-99e3-2282-b31f359b411a")) { // bike
            ret = "n";
        }
        else if (categoryId.equals("CAT-6c7de3f8-de6c-7061-1dd2-b093044014bf")) { // bank
            ret = "o";
        }
        else if (categoryId.equals("CAT-7cccbafa-87d7-c9a6-661b-8b3402fe9e78")) { // pet
            ret = "q";
        }
        else if (categoryId.equals("CAT-8edf9663-623e-4735-490e-31288f0a70b0")) { // gifts
            ret = "h";
        }
        else if (categoryId.equals("CAT-52fa4693-c088-afb2-2a99-7bc39bb23a0f")) { // health
            ret = "i";
        }
        else if (categoryId.equals("CAT-79b02f2f-2adc-88f0-ac2b-4e71ead9cfc8")) { // bills
            ret = "b";
        }
        else if (categoryId.equals("CAT-94b11142-e97b-941a-f67f-6e18d246a23f")) { // business
            ret = "c";
        }
        else if (categoryId.equals("CAT-7829f71c-2e8c-afa5-2f55-fa3634b89874")) { // auto
            ret = "a";
        }
        else if (categoryId.equals("CAT-aad51b46-d6f7-3da5-fd6e-492328b3023f")) { // shopping
            ret = "r";
        }
        else if (categoryId.equals("CAT-b709172b-4eb7-318e-3b5d-e0f0500b32ac")) { // housing
            ret = "j";
        }
        else if (categoryId.equals("CAT-bce48142-fea4-ff45-20d9-0a642d44de83")) { // transfer
            ret = "t";
        }
        else if (categoryId.equals("CAT-bd56d35a-a9a7-6e10-66c1-5b9cc1b6c81a")) { // food
            ret = "g";
        }
        else if (categoryId.equals("CAT-bf5c9cca-c96b-b50d-440d-38d9adfda5b0")) { // education
            ret = "d";
        }
        else if (categoryId.equals("CAT-bf9f3294-4c40-1677-d269-54fbc189faf3")) { // piggy
                                                                                  // banks
            ret = "k";
        }
        else if (categoryId.equals("CAT-ccd42390-9e8c-3fb6-a5d9-6c31182d9c5c")) { // investments
            ret = "m";
        }
        else if (categoryId.equals("CAT-d00fc539-aa14-009b-4ffb-7e8c7b839954")) { // tax
            ret = "s";
        }
        else if (categoryId.equals("CAT-d73ee74b-13a4-ac3e-4015-fc4ba9a62b2a")) { // fees
            ret = "f";
        }
        else if (categoryId.equals("CAT-d7851c65-3353-e490-1953-fb9235e681e4")) { // uncategorized
            ret = "v";
        }
        else if (categoryId.equals("CAT-e04e9d1e-e041-c315-2e50-094143ab3f73")) { // entertainment
            ret = "e";
        }
        else if (categoryId.equals("CAT-e5154228-fe45-790d-a280-f6bf5ae5ac9f")) { // personal
                                                                                  // care
            ret = "p";
        }
        else if (categoryId.equals("CAT-ea23d844-cbd1-eb10-f6ac-0df9610e59ae")) { // travel
            ret = "u";
        }

        // demo data
        if (categoryId.equals("9a63ecd4-eef3-4ab2-8101-d2a15ccc3470")) { // bike
            ret = "n";
        }
        else if (categoryId.equals("f2bab5a3-1de2-4b3a-a51c-38279f0ef3b4")) { // bank
            ret = "o";
        }
        else if (categoryId.equals("bd213200-14b2-46d3-bb4c-22ec50a29e5f")) { // pet
            ret = "q";
        }
        else if (categoryId.equals("666f0027-c821-4f5b-bf7b-e435849b264c")) { // gifts
            ret = "h";
        }
        else if (categoryId.equals("793bb6a4-2d70-41af-b9af-ed3c81977df1")) { // health
            ret = "i";
        }
        else if (categoryId.equals("abfc0603-57f4-4cdc-ae69-d3509c847366")) { // bills
            ret = "b";
        }
        else if (categoryId.equals("e8e7fee4-83cc-4d88-8575-89645aa66334")) { // business
            ret = "c";
        }
        else if (categoryId.equals("787481a6-ff1b-44e9-8782-9796c68be2f2")) { // auto
            ret = "a";
        }
        else if (categoryId.equals("7d1a04ae-80d1-4a26-bed0-007849e96979")) { // shopping
            ret = "r";
        }
        else if (categoryId.equals("38810b0d-8a1b-44ed-b537-c19a086549a1")) { // housing
            ret = "j";
        }
        else if (categoryId.equals("1ce9a23e-fbcc-481f-b478-7fd792333f4c")) { // transfer
            ret = "t";
        }
        else if (categoryId.equals("83b5d02e-3700-445c-9842-1e14e70d3529")) { // food
            ret = "g";
        }
        else if (categoryId.equals("ceca6857-dfb8-4cb8-b0ea-7156bc0cc40c")) { // education
            ret = "d";
        }
        else if (categoryId.equals("f2bab5a3-1de2-4b3a-a51c-38279f0ef3b4")) { // piggy
                                                                              // banks
            ret = "k";
        }
        else if (categoryId.equals("b96f0d88-a9dc-4e55-889d-a9ac81e85ba9")) { // investments
            ret = "m";
        }
        else if (categoryId.equals("bb6d9961-d791-4b26-aafd-34718fc5b175")) { // tax
            ret = "s";
        }
        else if (categoryId.equals("dbf0224e-a94c-40e0-88af-2f07ca1cedc1")) { // fees
            ret = "f";
        }
        else if (categoryId.equals("c8b330c9-3d0f-4c5a-bb7b-82a33839431a")) { // uncategorized
            ret = "v";
        }
        else if (categoryId.equals("bcf47747-6510-4840-802d-1a9900429da9")) { // entertainment
            ret = "e";
        }
        else if (categoryId.equals("ff748236-9863-448b-9503-28ab3e48e578")) { // personal
                                                                              // care
            ret = "p";
        }
        else if (categoryId.equals("4cc82d0d-f392-4bc4-8e46-717019edef9f")) { // travel
            ret = "u";
        }

        return ret;
    }

    // KEEP METHODS END

}
