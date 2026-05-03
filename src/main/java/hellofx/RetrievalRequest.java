package hellofx;

public class RetrievalRequest {
    private String itemName;
    private String claimantName;
    private String claimantEmail;
    private String ownershipProof;
    private String attachFile;
    private String dateLost;
    private boolean approved;

    public RetrievalRequest(String itemName, String claimantName, String claimantEmail,
                             String ownershipProof, String attachFile, String dateLost) {
        this.itemName = itemName;
        this.claimantName = claimantName;
        this.claimantEmail = claimantEmail;
        this.ownershipProof = ownershipProof;
        this.attachFile = attachFile;
        this.dateLost = dateLost;
        this.approved = false;
    }

    public String getItemName() { return itemName; }
    public String getClaimantName() { return claimantName; }
    public String getClaimantEmail() { return claimantEmail; }
    public String getOwnershipProof() { return ownershipProof; }
    public String getAttachFile() { return attachFile; }
    public String getDateLost() { return dateLost; }
    public boolean isApproved() { return approved; }
    public void approve() { this.approved = true; }
}