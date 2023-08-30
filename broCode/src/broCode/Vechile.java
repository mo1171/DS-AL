package broCode;

      class Vechile implements VechileInterface  {
String date  ; 	
	 Vechile(String date) {
		 
		 System.out.println("we got vechile ");
		 this.date = date ; 
	}
	 @Override
		public void drive() {
			System.out.println("parent body");
		}
		void showinfo() {
			System.out.println("the date is " + this.date) ; 
		}

		@Override
		public String toString() {
			return "date=" + date + "]";
		}
		
		
		
}
