// [Template no Kotlin Playground](https://pl.kotl.in/WcteahpyN)

enum class TipoLog{
    INFO,
    ERROR
}

enum class Nivel {
    BASICO,
    INTERMEDIARIO,
    AVANCADO
}

class LogTrace{

    companion object Bonger { 

        fun log(tipo: TipoLog = TipoLog.INFO, mensagem: String, informacao: String? = null) {             
            println("[$tipo] - $mensagem")
            informacao?.let{
                println(it)
            }
        }

    }
}

data class Usuario(val matricula: Int, var nome: String, var email: String) {

    override fun equals(other: Any?) = other is Usuario && other.matricula == this.matricula
}

data class ConteudoEducacional(val id: Int, var nome: String, val duracao: Int = 60){

    override fun equals(other: Any?) = other is ConteudoEducacional && other.id == this.id
}

data class Formacao(val nome: String, val nivel: Nivel,val conteudos: MutableSet<ConteudoEducacional> = mutableSetOf()) {

    val inscritos = mutableSetOf<Usuario>()

    fun adicionarConteudo(conteudo: ConteudoEducacional){
        if(conteudos.add(conteudo)){
            LogTrace.log(mensagem = "Conteudo adicionado com sucesso", informacao = "${nome.toUpperCase()} - $nivel | ${conteudo.nome} - ${conteudo.duracao}")
        }else{
            LogTrace.log(TipoLog.ERROR, "Erro ao adicionar conteudo")
        }
    }

    fun removerConteudo(id: Int){
        buscarConteudo(id)?.let{
            conteudos.remove(it)
            LogTrace.log(mensagem = "Conteudo deletado com sucesso da formação $nome")
        }?: LogTrace.log(TipoLog.ERROR, "Conteúdo não encontrado")
    }

    fun atualizarConteudo(id: Int, nomeUpdate: String){
        buscarConteudo(id)?.let{
            it.nome = nomeUpdate
            LogTrace.log(mensagem = "Conteudo atualizado com sucesso na formação $nome")
        }?: LogTrace.log(TipoLog.ERROR, "Usuário não encontrado")
    }

    fun matricular(usuario: Usuario) {
        if(inscritos.add(usuario)){
            LogTrace.log(mensagem = "Usuário adicionado com sucesso", informacao = "${nome.toUpperCase()} - $nivel | ${usuario.matricula} - ${usuario.nome}")
        }else{
            LogTrace.log(TipoLog.ERROR, "Erro ao realizar matricula")
        } 
    }

    fun cancelarMatricula(matricula: Int){
        buscarUsuario(matricula)?.let{
            inscritos.remove(it)
            LogTrace.log(mensagem = "Matrícula cancelada com sucesso na formação $nome")
        }?: LogTrace.log(TipoLog.ERROR, "Matrícula não encontrada") 
    }

    fun listarUsuario(matricula: Int){
        buscarUsuario(matricula)?.let{
            println("[Aluno] - ${it.nome}")
        }?: LogTrace.log(TipoLog.ERROR, "Usuário não encontrado")
    }

    fun listarUsuarios() {
        println("\t \t ----- \t \t")
        println("Alunos da formação $nome de nível $nivel")
        inscritos.map{println("|${it.matricula}|${it.nome}|${it.email}")}
        println("\t \t ----- \t \t")
    }

    fun listarConteudo(id: Int){
        buscarConteudo(id)?.let{
            println("[Conteúdo] - ${it.nome}")
        }?: LogTrace.log(TipoLog.ERROR, "Conteúdo não encontrado")
    }

    fun listarConteudos(){
        println("\t \t ----- \t \t")
        println("Conteudos da formação $nome de nível $nivel")
        conteudos.map{println("|${it.nome}|${it.duracao} minutos")}
        println("\t \t ----- \t \t")
    }

    fun buscarUsuario(matricula: Int): Usuario?{
        return inscritos.find{ it.matricula.equals(matricula)}
    }

    fun buscarConteudo(id: Int) : ConteudoEducacional?{
        return conteudos.find{ it.id.equals(id)}
    }
}

fun printTeste(mensagem: String, numero: Int){
        println(" \n[TESTE $numero] - $mensagem \n")
    }

fun main() {
    
    //criando alguns usuários
    val user1 = Usuario(1, "user1", "user1@user.com") //(matrícula, nome, email)
    val user2 = Usuario(2, "user2", "user2@user.com") //(matrícula, nome, email)
    val user3 = Usuario(3, "user3", "user3@user.com") //(matrícula, nome, email)

    //criando alguns conteudos
    val conteudo1 = ConteudoEducacional(1, "Introdução ao kotlin", 120) //(id, nome, duração)
    val conteudo2 = ConteudoEducacional(2, "Comandos kotlin", 140) //(id, nome, duração)
    val conteudo3 = ConteudoEducacional(3, "Funções kotlin") //(id, nome, duração)
    val conteudo4 = ConteudoEducacional(4, "Introdução ao Java", 120) //(id, nome, duração)
    val conteudo5 = ConteudoEducacional(5, "Comandos Java", 140) //(id, nome, duração)
    val conteudo6 = ConteudoEducacional(6, "Funções Java") //(id, nome, duração)
    val conteudo7 = ConteudoEducacional(7, "Entendendo SQL", 120) //(id, nome, duração)
    val conteudo8 = ConteudoEducacional(8, "Comandos SQL", 140) //(id, nome, duração)
    val conteudo9 = ConteudoEducacional(9, "Criando querys") //(id, nome, duração)
    
    //criando algumas formações
    val formacaoKotlin = Formacao("Formação Kotlin", Nivel.BASICO, mutableSetOf(conteudo1, conteudo2, conteudo3)) //(nome, nível, conteúdos)
    val formacaoJava = Formacao("Formação Java", Nivel.BASICO, mutableSetOf(conteudo4, conteudo5, conteudo6)) //(nome, nível, conteúdos)
    val formacaoSQL = Formacao("Formação SQL", Nivel.BASICO, mutableSetOf(conteudo7, conteudo8, conteudo9)) //(nome, nível, conteúdos)

    //criando usuário e conteudo duplicados
    val userCopy = user1.copy()
    val conteudoCopy = conteudo4.copy()

    //criando novo conteúdo
    val newConteudo = ConteudoEducacional(10, "Exceptions Java", 120) //(id, nome, duração)

    //listando os conteudos da formação Java
    printTeste("listando os conteudos da formação Java", 1)
    formacaoJava.listarConteudos()

    //listando conteudo específico da formação Java
    printTeste("listando conteudo específico da formação Java", 2)
    formacaoJava.listarConteudo(4) //(id)

    //listando usuários da formação Java
    printTeste("listando usuários da formação Java", 3)
    formacaoJava.listarUsuarios()

    //listando usuário específico da formação Java - [espera-se erro pois não tem nenhum usuário cadastrado]
    printTeste("listando usuário específico da formação Java - [espera-se erro pois não tem nenhum usuário cadastrado]", 4)
    formacaoJava.listarUsuario(1) //(matricula)

    //matriculando 3 usuários a formação Java
    printTeste("matriculando 3 usuários a formação Java", 5)
    formacaoJava.matricular(user1) //(usuário)
    formacaoJava.matricular(user2) //(usuário)
    formacaoJava.matricular(user3) //(usuário)

    //listando os usuários da formação Java
    printTeste("listando os usuários da formação Java", 6)
    formacaoJava.listarUsuarios()

    //listando usuário específico da formação Java
    printTeste("listando usuário específico da formação Java", 7)
    formacaoJava.listarUsuario(1) //(matricula)

    //matriculando usuário com matrícula já existente na formação Java - [espera-se erro pois o usuário já existe]
    printTeste("matriculando usuário com matrícula já existente na formação Java - [espera-se erro pois o usuário já existe]", 8)
    formacaoJava.matricular(userCopy) //(usuário)

    //cancelando matricula de um usuário da formação Java
    printTeste("cancelando matricula de um usuário da formação Java", 9)
    formacaoJava.cancelarMatricula(3) //(matricula)

    //adicionando novo conteudo a formação Java
    printTeste("adicionando novo conteudo a formação Java", 10)
    formacaoJava.adicionarConteudo(newConteudo) //(conteúdo)

    //listando conteudos da formação Java
    printTeste("listando conteudos da formação Java", 11)
    formacaoJava.listarConteudos()

    //adicionando conteudo com id já existente na formação Java - [espera-se erro pois o conteúdo já existe]
    printTeste("adicionando conteudo com id já existente na formação Java - [espera-se erro pois o conteúdo já existe]", 12)
    formacaoJava.adicionarConteudo(conteudoCopy) //(conteúdo)

    //atualizando nome de um conteudo já existente na formação Java - [supõe-se que ao cadastrar o admin tenha errado o nome do conteúdo por acidente]
    printTeste("atualizando nome de um conteudo já existente na formação Java - [supõe-se que ao cadastrar o admin tenha errado o nome do conteúdo por acidente]", 13)
    formacaoJava.atualizarConteudo(4,"Introdução a linguagem de programação Java") //(id, novo_nome)

    //listando conteudos da formação Java
    printTeste("listando conteudos da formação Java", 14)
    formacaoJava.listarConteudos()

    //listando usuáriso da formação Java
    printTeste("listando usuários da formação Java", 15)
    formacaoJava.listarUsuarios()

}
