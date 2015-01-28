<?php
// src/FormData.php
/**
 *	@Entity @Table(name="FormData")
 */
class FormData
{
    /** @Id @Column(type="integer") @GeneratedValue
     **/
    protected $id;
    
    /** @Column(type="text")
     **/
    protected $data;

    /** @Column(type="datetime")
     **/
	protected $submissionDate;   
	
	public function getId() {
		return $this->id;
	}
	
	public function getData() {
		return $this->data;
	}
	
	public function setData($data) {
		$this->data = $data;
	}
	
	public function getSubmissionDate() {
		return $this->submissionDate;
	}
	
	public function setSubmissionDate($date) {
		$this->submissionDate = $date;
	}
}
?>