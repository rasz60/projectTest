$(document).ready(function () {
   
   let images = $('.images').val();
   let arrImages=images.split('/');
   let beforeImg ='';
   let imgView ='';
   let deleteImg='';
   
   for(var i=0; i<arrImages.length-1; i++){
      console.log(arrImages[i]);
      beforeImg +='<img src="../images/'+arrImages[i]+'" style="width :23%">';
      beforeImg +='<i class="fa-solid fa-x rebefore" index="'+i+'"></i>';
      beforeImg +='<br/>';
      
      images+=arrImages[i]+'/';
   }
      
   $('.beforeImg').html(beforeImg);

   
   $(document).on('click','.rebefore',function(){
      let index = $(this).attr('index');

      deleteImg+=arrImages[index]+'/';

      arrImages.splice(index,1)

      beforeImg ='';
      images ='';
      for(var i=0; i<arrImages.length-1; i++){
         beforeImg +='<img src="../images/'+arrImages[i]+'" style="width :23%">';
         beforeImg +='<i class="fa-solid fa-x rebefore" index="'+i+'"></i>';
         beforeImg +='<br/>';
         
         images+=arrImages[i]+'/';
      }
      
      console.log(images);
      $('.beforeImg').html(beforeImg);
      $('.images').val(images);
      $('.deleteImg').val(deleteImg);
      
   })
   
   
   $('.content').keyup(function () {
      console.log(images);
      console.log(arrImages);
      let tmp = $(this).val().length;
      
      if (tmp != 0 || tmp != '') {
          $('.textCount').text(tmp + '/300자');
      }  
      if (tmp > 299) {
         tmp.substring(0, 299);
      };
      console.log(tmp);
   });
   
   
   $('.hashtag').keyup(function () {
      let element = $(this);
      let count = element.val().split('#').length-1;
      let hashtag = element.val().split('#');
      let result = '';
      let hashCheck =element.val().substr(0,1);

      if(hashCheck !=='#'){
         alert('해쉬태그는 #을 붙여주세요! ');
         element.val('#');
      }
      
      if(count>=11){
         for(let i=0; i<11; i++){
            if(i==0){
               result=hashtag[0];
            }else{
               
            result +='#'+hashtag[i];
            }
         }
         alert('해쉬태그는 10개까지만 등록가능합니다\n'+result);
         element.val(result); 
      }else{
         for(let i=0; i<hashtag.length; i++){
            element.val(element.val().replaceAll(/##/gi, "#")); 
      
            if(hashtag[i]!='' && hashtag[i].replaceAll(' ','')==''){
               element.val(result+'#');    
            }
            
            if(i==0){
               result=hashtag[0];
            
            }else{
               result +='#'+hashtag[i];                           
            }
         
         }
      }   
         console.log(result);
      
      $(this).attr('value',result);
   });
   
   $(document).on('click','.reimg',function(){
      
      const dataTransfer = new DataTransfer(); 
      let changeData ="";
      let arr = $('.img')[0].files;
      let index = $(this).attr('index');
         
      let fileArray = Array.from(arr); //변수에 할당된 파일을 배열로 변환(FileList -> Array) 
      fileArray.splice(index, 1); //해당하는 index의 파일을 배열에서 제거 
      fileArray.forEach(file => { dataTransfer.items.add(file); }); //남은 배열을 dataTransfer로 처리(Array -> FileList) 
      $('.img')[0].files = dataTransfer.files; //제거 처리된 FileList를 돌려줌
         
      changeData = $('.img')[0].files;
      imgView='';
      for(var i=0; i<changeData.length; i++){
         imgView +='<img src="'+URL.createObjectURL(changeData[i])+'" style="width :23%">';
         imgView +='<i class="fa-solid fa-x reimg" index="'+i+'"></i>';
         imgView +='<br/>';
      }
         imgView +='<i class="fa-brands fa-instagram addImgBtn" style="color : red; font-size: 220px;"></i>';
         
         //동일 이미지 등록 가능 (추가)
         $('.addImg').val('');
         
      $('.imgView').html(imgView);         
   });
   
   $('.addImg').change(function(){ //파일 추가
      const dataTransfer = new DataTransfer(); 
      let arr = $('.img')[0].files;
      let arr2 =$('.addImg')[0].files;
      let extension =''; //확장자
      let totalSize = 0;
      
   
      
      for(var i=0; i<arr.length; i++){ //사진 용량 확인 1
         totalSize+=arr[i].size;
      }
      
      for(var i=0; i<arr2.length; i++){ //사진 용량 확인 1
         totalSize+=arr2[i].size;      
         
      }
      
      if(arr.length+arr2.length+(arrImages.length-1)>10){
         alert('10장 이상 등록할수 없습니다.\n다시 선택해주세요');

      }else if (totalSize>10000000-1){
         alert('이미지의 총 용량이 10MB를 초과합니다.\n 다른 이미지를 올려주세요');
         
      }else{
         let fileArray = Array.from(arr); //변수에 할당된 파일을 배열로 변환(FileList -> Array) 
         
         for(var i=0; i<arr2.length; i++){            
            extension = arr2[i].name.substring(arr2[i].name.lastIndexOf('.')+1).toLowerCase(); //확장자명 추출

            if(extension =='jpg' || extension =='jpeg' || extension =='png' || extension ==''){   //확장자 확인      
               fileArray.push(arr2[i]);            
            
            }else{
               alert(arr2[i].name+'은 지원하지 않은 확장자 파일입니다.');
            }
         }
         
         fileArray.forEach(file => { dataTransfer.items.add(file); }); //남은 배열을 dataTransfer로 처리(Array -> FileList) 
         $('.img')[0].files = dataTransfer.files; 
         arr = $('.img')[0].files;
         
      }
      

      imgView='';
      
      for(var i=0; i<arr.length; i++){
         
         imgView +='<img src="'+URL.createObjectURL(arr[i])+'" style="width :23%">'
         imgView +='<i class="fa-solid fa-x reimg" index="'+i+'"></i>';
         imgView +='<br/>'
      }
      if(arr.length<10){
         imgView +='<i class="fa-brands fa-instagram addImgBtn" style="color : red; font-size: 220px;"></i>';         
      }
      $('.imgView').html(imgView);
      $('.addImg').val('');   
   });
   
   $(document).on('click','.addImgBtn',function(){
      $('input[name=addImg]').trigger('click');
   })

});