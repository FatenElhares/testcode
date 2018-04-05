node {

    checkout scm 
    try {
        stage 'Run unit/integration '
        sh 'make testbuild'
        
        stage 'Build application artefacts'
        sh 'make release'
        
        
    }
    finally{
        sh 'make clean'
        sh 'make logout'
       
    }
}
