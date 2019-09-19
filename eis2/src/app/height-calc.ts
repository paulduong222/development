export class HeightCalc { 

    private static defaultHeight = '800px';
 
    private static calcValues = {
      'inc' : {1080 : 0.941433, 1050 : 0.93936, 1024 : 0.93428, 
         960 : 0.93268, 900 : 0.921564, 800 : 0.906828, 768 : 0.8992},
 
      'res' : {1080 : 0.88913874,  1050 : 0.8842337, 1024 : 0.879682, 
         960 : 0.865361, 900 : 0.8507266, 800 : 0.82343988, 768 : 0.8128},
 
      'timecost' : {1080 : 0.91688687,  1050 : 0.9129, 1024 : 0.909194, 
         960 : 0.8984, 900 : 0.887715, 800 : 0.86149163, 768 : 0.8528},

      'adminoffice' : {1080 : 0.9413, 1050 : 0.93936, 1024 : 0.93757, 
         960 : 0.93268, 900 : 0.92206, 800 : 0.9071537, 768 : 0.8992},

      'cont' : {1080 : 0.9359658, 1050 : 0.931642778, 1024 : 0.93189557, 
         960 : 0.92656, 900 : 0.918097754, 800 : 0.9086758, 768 : 0.9024},

      'importexport' : {1080 : 0.941302, 1050 : 0.938258, 1024 : 0.94438139, 
         960 : 0.93145655, 900 : 0.9273448, 800 : 0.9147641, 768 : 0.9104},

      'useracct' : {1080 : 0.9348986, 1050 : 0.9327453, 1024 : 0.93189557, 
         960 : 0.9253366, 900 : 0.91809775, 800 : 0.9086758, 768 : 0.9008},

      'dbmgmt' : {1080 : 0.97438634, 1050 : 0.973539, 1024 : 0.97275823, 
         960 : 0.9706242, 900 : 0.9669749, 800 : 0.96347032, 768 : 0.9616},

      'rossimp' : {1080 : 0.78762, 1050 : 0.7816979, 1024 : 0.77412032,
         960 : 0.7576499, 900 : 0.7344782, 800 : 0.91628614, 768 : 0.9104,},

      'rossimportstart' : {1080 : 0.704375667, 1050 : 0.69459757, 1024 : 0.6867196,
         960 : 0.6609547, 900 : 0.6354029, 800 : 0.5814307, 768 : 0.5616},

      'costaccr' : { 1080 : 0.8548559, 1050 : 0.85115766, 1024 : 0.85925085,
         960 : 0.83965728, 900 : 0.82959049, 800 : 0.809741248, 768 : 0.8032},

      'adminoffice2' : {1080 : 0.94023479, 1050 : 0.93825799, 1024 : 0.936435868,
         960 : 0.93145655, 900 : 0.92338177, 800 : 0.904109589, 768 : 0.896}
    };
     
 
    public static calculateHeight(tab: string){
       const screenHeight = window.screen.height;
       const screenRes = this.calcValues[tab];
       if(screenRes.hasOwnProperty(screenHeight)){
          return (window.innerHeight * screenRes[screenHeight]) + 'px';
       }
       else {
          return this.defaultHeight;
       }
    }
 }