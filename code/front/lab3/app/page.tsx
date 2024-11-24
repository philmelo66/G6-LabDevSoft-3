import { title, subtitle } from "@/components/primitives";

export default function Home() {
  return (
    <section className="flex flex-col items-center justify-center gap-4 py-8 md:py-10">
      <div className="inline-block max-w-xl text-center justify-center">
        <span className={title()}>Resgate seu&nbsp;</span>
        <span className={title({ color: "violet" })}>Mérito&nbsp;</span>
        <br />
        <span className={title()}>como aluno.</span>
        <div className={subtitle({ class: "mt-4" })}>
          Acesse o sistema e veja como você pode resgatar seus méritos como
          aluno.
        </div>
      </div>
    </section>
  );
}
