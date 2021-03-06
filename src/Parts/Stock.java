package Parts;

import Main.TaggedDataList;

/**
 * Stocks are weapon attachments. Stocks are designed to be usable on
 * more than one receiver but have different stats for each one. For this reason
 * most of the fields in this class are arrays where each index corresponds to
 * a different receiver.
 */
public class Stock extends WeaponPart
{

    /**
     * A copy of the StandardDefinitions file for code shortening.
     */
    private StandardDefinitions sd = new StandardDefinitions();
    
    /**
     * Modification to spread done when firing while aiming
     */
    private double[] spreadAim = new double[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Modification to spread done when firing from the hip
     */
    private double[] spreadHip = new double[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Modification to max base spread done when firing while aiming
     */
    private double[] spreadAimMax = new double[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Modification to max base spread done when firing from the hip
     */
    private double[] spreadHipMax = new double[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Modification to lower range. Weapon damage begins to drop off after shot travels this
     * distance (meters)
     */
    private int[] rangeLow = new int[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Modification to max range. No damage is done after shot travels this distance (meters)
     */
    private int[] rangeMax = new int[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Modification of players run speed
     */
    private double[] runSpeed = new double[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Base recoil
     */
    private double[] recoil = new double[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Modification to the players scopeInTime.
     */
    private double[] scopeInTime = new double[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Create a new Stock with data from a TaggedDataList.
     *
     * @param tdList The TaggedDataList holding the data relevant to this Magazine.
     */
    public Stock(final TaggedDataList tdList)
    {
        fillData(tdList);
    }

    /**
     * Goes through the TaggedDataList made by the XMLReader. Looks for tag names that are
     * relevant to this type of object and modifies data accordingly. Passes the TaggedDataList
     * to the super class when finished.
     * 
     * @param tdList The TaggedDataList containing the relevant data.
     */
    @Override
    public final void fillData(final TaggedDataList tdList)
    {
        String dataString;
        double dataDouble;

        dataType type;
        String tagName;
        int weaponIndex = 0;
        boolean usedData;

        for (int i = 0; i < tdList.getNumberOfItems(); i++)
        {
            dataString = "NO DATA FOUND";
            dataDouble = Double.NEGATIVE_INFINITY;
            usedData = true;

            type = tdList.getTypeByIndex(i);
            tagName = tdList.getItemKeyByIndex(i);

            if (type == dataType.DOUBLE)
            { // Double
                dataDouble = Double.parseDouble(tdList.getItemDataByIndex(i));
            }
            else
            {
                dataString = tdList.getItemDataByIndex(i);
            }

            switch (tagName.toLowerCase())
            {
                case "matchesweapon":
                    weaponIndex = getIndexOfWeaponShortName(dataString);
                    setUsableOn(weaponIndex, true);
                    break;
                case "spreadaim":
                    this.setSpreadAim(weaponIndex, dataDouble);
                    break;
                case "spreadhip":
                    this.setSpreadHip(weaponIndex, dataDouble);
                    break;
                case "spreadaimmax":
                    this.setSpreadAimMax(weaponIndex, dataDouble);
                    break;
                case "spreadhipmax":
                    this.setSpreadHipMax(weaponIndex, dataDouble);
                    break;
                case "rangelow":
                    this.setRangeLow(weaponIndex, (int) dataDouble);
                    break;
                case "rangemax":
                    this.setRangeMax(weaponIndex, (int) dataDouble);
                    break;
                case "runspeed":
                    this.setRunSpeed(weaponIndex, dataDouble);
                    break;
                case "recoil":
                    this.setRecoil(weaponIndex, dataDouble);
                    break;
                case "scopeintime":
                    this.setScopeInTime(weaponIndex, dataDouble);
                    break;
                default:
                    usedData = false;
                    break;
            }
            if (usedData)
            {
                tdList.flagDataAsUsed(i);
            }
        }
        tdList.clearUsedData();
        super.fillData(tdList);
    }

    /**
     * @return The String representation of this object.
     */
    @Override
    public final String toString()
    {
        StringBuilder returnString = new StringBuilder(super.toString());
        for (int i = 0; i < weaponShortNames.length; i++)
        {
            if (getUsableOn(i))
            {
                returnString.append(getWeaponShortName(i) + " ------------\n");
                returnString.append("Spread Hip/Max: " + spreadHip[i] + "/" + spreadHipMax[i] + "\n");
                returnString.append("Spread Aim/Max: " + spreadAim[i] + "/" + spreadAimMax[i] + "\n");
                returnString.append("Range Low/Max:  " + rangeLow[i] + "/" + rangeMax[i] + "\n");
                returnString.append("Run Speed: \t" + runSpeed[i] + "\n");
                returnString.append("Recoil: \t" + recoil[i] + "\n");
            }
        }
        returnString.append("===========================\n");

        return returnString.toString();
    }

    /**
     * @param index The receiver index
     * @return The spreadAim for this Muzzle when applied to receiver index
     */
    public final double getSpreadAim(final int index)
    {
        return spreadAim[index];
    }

    /**
     * Sets the damage for this Muzzle when applied to receiver index
     *
     * @param index        The receiver index
     * @param newSpreadAim The spreadAim
     */
    private void setSpreadAim(final int index, final double newSpreadAim)
    {
        this.spreadAim[index] = newSpreadAim;
    }

    /**
     * @param index The receiver index
     * @return The spreadHip for this Muzzle when applied to receiver index
     */
    public final double getSpreadHip(final int index)
    {
        return spreadHip[index];
    }

    /**
     * Sets the damage for this Muzzle when applied to receiver index
     *
     * @param index        The receiver index
     * @param newSpreadHip The spreadHip
     */
    private void setSpreadHip(final int index, final double newSpreadHip)
    {
        this.spreadHip[index] = newSpreadHip;
    }

    /**
     * @param index The receiver index
     * @return The spreadAimMax for this Muzzle when applied to receiver index
     */
    public final double getSpreadAimMax(final int index)
    {
        return spreadAimMax[index];
    }

    /**
     * Sets the damage for this Muzzle when applied to receiver index
     *
     * @param index           The receiver index
     * @param newSpreadAimMax The spreadAimMax
     */
    private void setSpreadAimMax(final int index, final double newSpreadAimMax)
    {
        this.spreadAimMax[index] = newSpreadAimMax;
    }

    /**
     * @param index The receiver index
     * @return The spreadHipMax for this Muzzle when applied to receiver index
     */
    public final double getSpreadHipMax(final int index)
    {
        return spreadHipMax[index];
    }

    /**
     * Sets the damage for this Muzzle when applied to receiver index
     *
     * @param index           The receiver index
     * @param newSpreadHipMax The spreadHipMax
     */
    private void setSpreadHipMax(final int index, final double newSpreadHipMax)
    {
        this.spreadHipMax[index] = newSpreadHipMax;
    }

    /**
     * @param index The receiver index
     * @return The rangeLow for this Muzzle when applied to receiver index
     */
    public final int getRangeLow(final int index)
    {
        return rangeLow[index];
    }

    /**
     * Sets the damage for this Muzzle when applied to receiver index
     *
     * @param index       The receiver index
     * @param newRangeLow The rangeLow
     */
    private void setRangeLow(final int index, final int newRangeLow)
    {
        this.rangeLow[index] = newRangeLow;
    }

    /**
     * @param index The receiver index
     * @return The rangeMax for this Muzzle when applied to receiver index
     */
    public final int getRangeMax(final int index)
    {
        return rangeMax[index];
    }

    /**
     * Sets the damage for this Muzzle when applied to receiver index
     *
     * @param index       The receiver index
     * @param newRangeMax The rangeMax
     */
    private void setRangeMax(final int index, final int newRangeMax)
    {
        this.rangeMax[index] = newRangeMax;
    }

    /**
     * Sets the modification to the time it takes to zoom in.
     *
     * @param index   The receiver index
     * @param scopeIn The scopeIn time
     */
    private void setScopeInTime(final int index, final double scopeIn)
    {
        this.scopeInTime[index] = scopeIn;
    }

    /**
     * @return The scope in time for a given receiver index
     * @param index The receiver index
     */
    public final double getScopeInTime(final int index)
    {
        return scopeInTime[index];
    }

    /**
     * Returns the runSpeed for this barrel for the receiver index.
     *
     * @param index The index of the weapon short name
     * @return runSpeed[index]
     */
    public final double getRunSpeed(final int index)
    {
        return runSpeed[index];
    }

    /**
     * Sets the runSpeed for this barrel for the given receiver index.
     *
     * @param index       The index of the weapon short name
     * @param newRunSpeed The value to be set
     */
    public final void setRunSpeed(final int index, final double newRunSpeed)
    {
        this.runSpeed[index] = newRunSpeed;
    }

    /**
     * @param index The receiver index
     * @return The rangeMax for this Muzzle when applied to receiver index
     */
    public final double getRecoil(final int index)
    {
        return this.recoil[index];
    }

    /**
     * Sets the damage for this Muzzle when applied to receiver index
     *
     * @param index     The receiver index
     * @param newRecoil The recoil
     */
    private void setRecoil(final int index, final double newRecoil)
    {
        this.recoil[index] = newRecoil;
    }
}
